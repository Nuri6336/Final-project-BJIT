package com.healthcare.decisionsupportservice.service;

import com.healthcare.decisionsupportservice.dto.MentalHealthExerciseDto;

import java.util.List;

public interface MentalHealthExerciseService {
    void createMentalHealthExercise(MentalHealthExerciseDto mentalHealthExerciseDTO) throws Exception;

    List<MentalHealthExerciseDto> getAllMentalHealthExercise() throws Exception;
}
