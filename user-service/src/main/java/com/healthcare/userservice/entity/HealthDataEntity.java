package com.healthcare.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "health_data")
public class HealthDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long healthDataId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    private Double height;
    private Double weight;
    private Double sleepTime;
    private Double bmi;
    private Double bmr;
    private Integer age;

    private String diabetesStatus;
    private String bloodPressure;
    private String allergies;

    private LocalDateTime timestamp;
}
