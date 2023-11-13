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
public class ProgressDataDto {

    private Long progress_id;
    private Long goalId;
    private double update;
    private LocalDateTime timestamp;
}
