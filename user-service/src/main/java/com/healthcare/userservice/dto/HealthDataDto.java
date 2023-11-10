package com.healthcare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthDataDto {
    private Long healthDataId;

    private Double height;
    private Double weight;
    private Double sleepTime;
    private Double bmi;
    private Double bmr;
    private Integer age;

    private String diabetesStatus;
    private String bloodPressure;
    private String allergies;
}
