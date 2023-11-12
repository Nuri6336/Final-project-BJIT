package com.healthcare.pharmacyservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pharmacy")
public class PharmacyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    private String medicineType;
    private String medicineName;
    private String medicineGenre;
    private String unit;
    private LocalDate expireDate;
    private String status;
}
