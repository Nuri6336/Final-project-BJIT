package com.healthcare.schedulingservice.dto;

import com.healthcare.schedulingservice.entity.ShiftEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ResponseScheduleDto {

    private Long scheduleId;
    private ShiftEntity shiftId;
    private String doctorId;
    private LocalDate scheduleDate;
    private LocalTime scheduleTime;
    private boolean availability;
}
