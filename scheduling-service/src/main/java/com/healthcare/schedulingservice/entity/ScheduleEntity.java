package com.healthcare.schedulingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="doctor_schedule")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private ShiftEntity shiftId;

    private String doctorId;
    private LocalDate scheduleDate;
    private LocalTime scheduleTime;
    private boolean availability;
}
