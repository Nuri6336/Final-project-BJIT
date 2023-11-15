package com.healthcare.decisionsupportservice.service.implementation;

import com.healthcare.decisionsupportservice.dto.MentalHealthExerciseDto;
import com.healthcare.decisionsupportservice.dto.MoodDto;
import com.healthcare.decisionsupportservice.dto.UserDto;
import com.healthcare.decisionsupportservice.entity.MentalHealthExerciseEntity;
import com.healthcare.decisionsupportservice.entity.MoodEntity;
import com.healthcare.decisionsupportservice.exception.MoodNotFoundException;
import com.healthcare.decisionsupportservice.exception.RecommendationNotFoundException;
import com.healthcare.decisionsupportservice.repository.MentalHealthExerciseRepository;
import com.healthcare.decisionsupportservice.repository.MoodRepository;
import com.healthcare.decisionsupportservice.service.MoodService;
import com.healthcare.decisionsupportservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoodServiceImpl implements MoodService {
    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private MentalHealthExerciseRepository mentalHealthExerciseRepository;

    @Autowired
    private UserService userService;

    @Override
    public void createMood(MoodDto moodDTO) throws Exception {
        MoodEntity moodEntity = new MoodEntity();
        UserDto userDTO = userService.getCurrentUser();

        moodEntity.setUserId(userDTO.getUserId());
        moodEntity.setMoodState(moodDTO.getMoodState());
        moodEntity.setStressType(moodDTO.getStressType());
        moodEntity.setMoodNote(moodDTO.getMoodNote());
        moodEntity.setTimeStamp(LocalDateTime.now());

        moodRepository.save(moodEntity);
    }

    @Override
    public List<MoodDto> getMood() throws Exception {
        UserDto userDTO = userService.getCurrentUser();

        List<MoodEntity> moodEntityList = moodRepository.findAllByUserId(userDTO.getUserId());
        List<MoodDto> moodDtoList = new ArrayList<>();
        for (MoodEntity moodEntity : moodEntityList) {
            moodDtoList.add(new ModelMapper().map(moodEntity, MoodDto.class));
        }
        return moodDtoList;
    }

    @Override
    public List<MentalHealthExerciseDto> getRecommendedExercises() throws Exception {
        Optional<MoodDto> moodDTO = getLastMood();
        if (moodDTO.isEmpty()) {
            throw new MoodNotFoundException("You need to enter your mood state.");
        }

        int moodScore = getMoodScore(moodDTO.get());
        List<MentalHealthExerciseEntity> mentalHealthExerciseEntities = mentalHealthExerciseRepository.findByExerciseScore(moodScore);

        if (mentalHealthExerciseEntities.isEmpty()) {
            throw new RecommendationNotFoundException("There is no recommendation for you still now!");
        }

        return mentalHealthExerciseEntities.stream()
                .map(exercise -> {
                    return new ModelMapper()
                            .map(exercise, MentalHealthExerciseDto.class);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MoodDto> getLastMood() throws Exception {
        UserDto userDTO = userService.getCurrentUser();
        MoodEntity moodEntity = moodRepository.findTopByUserIdOrderByTimeStampDesc(userDTO.getUserId());
        if (moodEntity != null) {
            MoodDto moodDTO = new ModelMapper().map(moodEntity, MoodDto.class);
            return Optional.of(moodDTO);
        } else {
            return Optional.empty();
        }
    }

    private int getMoodScore(MoodDto moodDTO) {
        int moodStateScore = 0, stressTypeScore = 0;
        switch (moodDTO.getMoodState()) {
            case "happy" -> moodStateScore = 1;
            case "normal" -> moodStateScore = 2;
            case "sad" -> moodStateScore = 3;
        }
        switch (moodDTO.getStressType()) {
            case "low" -> stressTypeScore = 1;
            case "normal" -> stressTypeScore = 2;
            case "high" -> stressTypeScore = 3;
        }
        return moodStateScore + stressTypeScore;
    }
}
