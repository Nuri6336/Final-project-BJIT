package com.healthcare.helpdeskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceAllocationDto {

    private long allocatedId;
    private long resourceId;
    private String purpose;
    private LocalDate startTime;
    private LocalDate endTime;
    private String doctorId;
}
