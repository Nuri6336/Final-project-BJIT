package com.healthcare.schedulingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {

    private Long scheduleId;
    private String doctorId;
    private LocalDate scheduleDate;
    private LocalTime scheduleTime;
    private boolean availability;
}
