package com.healthcare.schedulingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="shift")
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shiftId;

    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
}
