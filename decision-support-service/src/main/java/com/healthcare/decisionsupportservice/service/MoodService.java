package com.healthcare.decisionsupportservice.service;

import com.healthcare.decisionsupportservice.dto.MentalHealthExerciseDto;
import com.healthcare.decisionsupportservice.dto.MoodDto;

import java.util.List;
import java.util.Optional;

public interface MoodService {
    void createMood(MoodDto moodDTO) throws Exception;

    List<MoodDto> getMood() throws Exception;

    Optional<MoodDto> getLastMood() throws Exception;

    List<MentalHealthExerciseDto> getRecommendedExercises() throws Exception;
}
