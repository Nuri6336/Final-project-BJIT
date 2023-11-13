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
@Table(name = "patient_health_goal")
public class PatientHealthGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    private String patientId;
    private String goalName;
    private String goalDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String goalStatus;
}
