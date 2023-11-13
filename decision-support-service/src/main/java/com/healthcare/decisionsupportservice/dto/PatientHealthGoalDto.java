package com.healthcare.decisionsupportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientHealthGoalDto {

    private Long goalId;
    private String patientId;
    private String goalName;
    private String goalDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String goalStatus;
}

