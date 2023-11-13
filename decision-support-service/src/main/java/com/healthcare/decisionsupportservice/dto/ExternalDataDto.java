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
public class ExternalDataDto {

    private Long externalGoalId;
    private String patientId;
    private double externalProgress;
    private LocalDateTime externalUpdateTime;
}
