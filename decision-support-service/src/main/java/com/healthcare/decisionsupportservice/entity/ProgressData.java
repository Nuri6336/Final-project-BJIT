package com.healthcare.decisionsupportservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "progress_data")
public class ProgressData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progress_id;

    private Long goalId;
    private double update;
    private LocalDateTime timestamp;
}
