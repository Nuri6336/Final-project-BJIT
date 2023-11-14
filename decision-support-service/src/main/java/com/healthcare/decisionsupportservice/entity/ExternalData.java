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
@Table(name = "external_data")
public class ExternalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long externalGoalId;

    private String patientId;
    private double externalProgress;
    private LocalDateTime externalUpdateTime;
}
