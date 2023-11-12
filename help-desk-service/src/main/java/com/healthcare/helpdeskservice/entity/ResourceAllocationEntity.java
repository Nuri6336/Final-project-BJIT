package com.healthcare.helpdeskservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="resource_allocation")
public class ResourceAllocationEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long allocatedId;

    private long resourceId;
    private String purpose;
    private LocalDate startTime;
    private LocalDate endTime;
    private String doctorId;
}
