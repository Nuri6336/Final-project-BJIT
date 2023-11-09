package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientProfileDto;
import com.healthcare.userservice.entity.PatientEntity;
import com.healthcare.userservice.entity.UserEntity;
import com.healthcare.userservice.repository.PatientRepository;
import com.healthcare.userservice.repository.UserRepository;
import com.healthcare.userservice.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PatientServiceImplementation implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String updatePatientInfo(PatientEditDto patientEditDto) throws Exception {
        Long patientId = getCurrentUser().getUserId();

        UserEntity userEntity = userRepository.findById(patientId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        userEntity.setFname(patientEditDto.getFname());
        userEntity.setLname(patientEditDto.getLname());
        userEntity.setMobile(patientEditDto.getMobile());
        userEntity.setGender(patientEditDto.getGender());

        userRepository.save(userEntity);

        PatientEntity patientEntity = patientRepository
                .findByPatientUniqueId(getCurrentUser().getUniqueId())
                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

            patientEntity.setAge(patientEditDto.getAge());
            patientEntity.setPatientImage(patientEditDto.getPatientImage());
            patientEntity.setAddress(patientEditDto.getAddress());
            patientRepository.save(patientEntity);

        return "Updated successfully";
    }


    @Override
    public PatientProfileDto viewProfile() throws Exception {
        String patientId = getCurrentUser().getUniqueId();

        PatientEntity patientEntity = patientRepository.findByPatientUniqueId(patientId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        UserEntity userEntity = userRepository.findByUniqueId(patientId)
                .orElseThrow(()->new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));

        PatientProfileDto returnDto = getPatientProfileDto(patientEntity, userEntity);

        return returnDto;
    }

    private static PatientProfileDto getPatientProfileDto(PatientEntity patientEntity, UserEntity userEntity) {
        PatientProfileDto returnDto = new PatientProfileDto();

        returnDto.setAddress(patientEntity.getAddress());
        returnDto.setAge(patientEntity.getAge());
        returnDto.setPatientImage(patientEntity.getPatientImage());

        returnDto.setUniqueId(userEntity.getUniqueId());
        returnDto.setGender(userEntity.getGender());
        returnDto.setEmail(userEntity.getEmail());
        returnDto.setFname(userEntity.getFname());
        returnDto.setLname(userEntity.getLname());
        returnDto.setMobile(userEntity.getMobile());

        return returnDto;
    }

    private UserEntity getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new Exception(AppConstants.TOKEN_INVALID));
    }
}
