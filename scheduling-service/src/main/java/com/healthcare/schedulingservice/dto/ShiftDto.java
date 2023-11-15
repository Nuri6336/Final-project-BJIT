package com.healthcare.schedulingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftDto {

    private Long shiftId;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
}
