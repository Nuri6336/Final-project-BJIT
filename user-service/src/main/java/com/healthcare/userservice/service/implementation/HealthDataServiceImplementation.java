package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.HealthDataDto;
import com.healthcare.userservice.entity.HealthDataEntity;
import com.healthcare.userservice.entity.UserEntity;
import com.healthcare.userservice.repository.HealthDataRepository;
import com.healthcare.userservice.repository.UserRepository;
import com.healthcare.userservice.service.HealthDataService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HealthDataServiceImplementation implements HealthDataService {
    @Autowired
    private HealthDataRepository healthDataRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createHealthData(HealthDataDto healthDataDto) throws Exception {
        HealthDataEntity healthDataEntity = new HealthDataEntity();

        healthDataEntity.setUserEntity(getCurrentUser());
        healthDataEntity.setHeight(healthDataDto.getHeight());
        healthDataEntity.setWeight(healthDataDto.getWeight());
        healthDataEntity.setSleepTime(healthDataDto.getSleepTime());
        healthDataEntity.setBloodPressure(healthDataDto.getBloodPressure());
        healthDataEntity.setDiabetesStatus(healthDataDto.getDiabetesStatus());
        healthDataEntity.setAllergies(healthDataDto.getAllergies());

        Double height = healthDataDto.getHeight();
        Double weight = healthDataDto.getWeight();
        Integer age = healthDataDto.getAge();
        String gender = getCurrentUser().getGender();

        healthDataEntity.setBmi(
                Double.parseDouble(String.format("%.2f", weight / (height/100.0 * height/100.0)))
        );

        if ("male".equalsIgnoreCase(gender)) {
            healthDataEntity.setBmr(
                    Double.parseDouble(String.format("%.2f", 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
                    ))
            );
        } else if ("female".equalsIgnoreCase(gender)) {
            healthDataEntity.setBmr(Double.parseDouble(String.format("%.2f", 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)))

            );
        }

        healthDataEntity.setTimestamp(LocalDateTime.now());
        healthDataRepository.save(healthDataEntity);
    }

    @Override
    public HealthDataDto getHealthData() throws Exception {
        HealthDataEntity healthDataEntity = healthDataRepository.findTopByUserEntityOrderByTimestampDesc(getCurrentUser());;
        return new ModelMapper().map(healthDataEntity, HealthDataDto.class);
    }

    @Override
    public List<HealthDataDto> getAllHealthData() throws Exception {
        List <HealthDataEntity> allHealthDataEntity = healthDataRepository.findAllByUserEntity(getCurrentUser());
        List <HealthDataDto> allHealthDataDto = new ArrayList<>();
        for(HealthDataEntity healthDataEntity : allHealthDataEntity) {
            allHealthDataDto.add(new ModelMapper().map(healthDataEntity, HealthDataDto.class));
        }
        return allHealthDataDto;
    }

    private UserEntity getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new Exception(AppConstants.TOKEN_INVALID));
    }

    public List<HealthDataDto> getAllHealthDataById(Long userId) throws Exception{
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(()->new Exception("User not found!"));

        List<HealthDataEntity> healthDataEntity = healthDataRepository.findAllByUserEntity(userEntity);
        List<HealthDataDto> healthDataDtos = new ArrayList<>();

        for (HealthDataEntity healthDataEntity1: healthDataEntity) {
            healthDataDtos.add(new ModelMapper().map(healthDataEntity1, HealthDataDto.class));
        }

        return healthDataDtos;
    }
}
