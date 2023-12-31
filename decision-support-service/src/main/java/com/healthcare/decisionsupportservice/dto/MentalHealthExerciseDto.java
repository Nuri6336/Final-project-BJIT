package com.healthcare.decisionsupportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentalHealthExerciseDto {
    private Long id;
    private String exerciseTitle;
    private String exerciseDescription;
    private String exerciseInstruction;
    private String exerciseDuration;
    private Long exerciseScore;
}
