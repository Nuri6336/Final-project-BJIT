package com.healthcare.pharmacyservice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacyDto {

    private Long medicineId;
    private String medicineType;
    private String medicineName;
    private String medicineGenre;
    private String unit;
    private LocalDate expireDate;
    private String status;
}
