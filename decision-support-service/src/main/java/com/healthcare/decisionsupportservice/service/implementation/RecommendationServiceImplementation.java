package com.healthcare.decisionsupportservice.service.implementation;


import com.healthcare.decisionsupportservice.dto.HealthDataDto;
import com.healthcare.decisionsupportservice.dto.MoodDto;
import com.healthcare.decisionsupportservice.dto.RecommendationDto;
import com.healthcare.decisionsupportservice.dto.UserDto;
import com.healthcare.decisionsupportservice.entity.RecommendationEntity;
import com.healthcare.decisionsupportservice.exception.ValueNotFoundException;
import com.healthcare.decisionsupportservice.networkmanager.UserFeingClient;
import com.healthcare.decisionsupportservice.repository.MoodRepository;
import com.healthcare.decisionsupportservice.repository.RecommendationRepository;
import com.healthcare.decisionsupportservice.service.MoodService;
import com.healthcare.decisionsupportservice.service.RecommendationService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecommendationServiceImplementation implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private UserFeingClient userFeingClient;
    @Autowired
    private MoodService moodService;
    @Autowired
    private MoodRepository moodRepository;

    @Override
    public void createRecommendations(List<RecommendationDto> recommendationDto) {
        List<RecommendationEntity> recommendationEntity = new ArrayList<>();
        for (RecommendationDto recommendationDto1 : recommendationDto) {
            recommendationEntity.add(new ModelMapper().map(recommendationDto1, RecommendationEntity.class));
        }
        recommendationRepository.saveAll(recommendationEntity);
    }

    public Optional <RecommendationDto> getRecommendationByRecommendationId (Long recommendationId) throws Exception{
        Optional <RecommendationEntity> recommendationEntity = recommendationRepository.findById(recommendationId);
        if (recommendationEntity.isPresent()) {
            return Optional.of(new ModelMapper().map(recommendationEntity, RecommendationDto.class));
        } else {
            return Optional.empty();
        }
    }

    public  List<RecommendationDto> getDietRecommendations() {
        String bmiCategory = bmiCategory();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findByRecommendationTypeAndRecommendationCategory("diet",bmiCategory);
        List<RecommendationDto> recommendationDtos = new ArrayList<>();
        for (RecommendationEntity recommendationEntity: recommendationEntities) {
            recommendationDtos.add(new ModelMapper().map(recommendationEntity, RecommendationDto.class));
        }
        return recommendationDtos;
    }

    public  List<RecommendationDto> getExerciseRecommendations() {
        String bmiCategory = bmiCategory();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findByRecommendationTypeAndRecommendationCategory("exercise", bmiCategory);
        List<RecommendationDto> recommendationDtos = new ArrayList<>();
        for (RecommendationEntity recommendationEntity: recommendationEntities) {
            recommendationDtos.add(new ModelMapper().map(recommendationEntity, RecommendationDto.class));
        }
        return recommendationDtos;
    }

    public List<RecommendationDto> getMentalHealthRecommendations() throws Exception {
        MoodDto moodDto = moodService.getLastMood()
                .orElseThrow(() -> new ValueNotFoundException("Data not found."));
        String moodCategory = moodDto.getMoodState();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findByRecommendationTypeAndRecommendationCategory("mentalHealth", moodCategory);
        List<RecommendationDto> recommendationDtos = new ArrayList<>();
        for (RecommendationEntity recommendationEntity: recommendationEntities) {
            recommendationDtos.add(new ModelMapper().map(recommendationEntity, RecommendationDto.class));
        }
        return recommendationDtos;
    }

    public List<RecommendationDto> getSleepRecommendations()
    {
        String sleepCategory = sleepCategory();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findByRecommendationTypeAndRecommendationCategory("sleep", sleepCategory);
        List<RecommendationDto> recommendationDtos = new ArrayList<>();
        for (RecommendationEntity recommendationEntity: recommendationEntities) {
            recommendationDtos.add(new ModelMapper().map(recommendationEntity, RecommendationDto.class));
        }
        return recommendationDtos;
    }




    private String bmiCategory() {
        Double bmi = getUserHealthData().getBmi();
        if (bmi < 18.5) {
            return "underWeight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "normalWeight";
        } else {
            return "overWeight";
        }
    }

    private String sleepCategory() {
        Double sleepTime = getUserHealthData().getSleepTime();
        if (sleepTime < 7) {
            return "underSleep";
        } else if (sleepTime >= 7 && sleepTime <= 8) {
            return "normalSleep";
        } else {
            return "overSleep";
        }
    }

    private UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        return userFeingClient.userDetailsByEmail(userMail);
    }
    private HealthDataDto getUserHealthData() {
        return userFeingClient.getHealthData();
    }
}
