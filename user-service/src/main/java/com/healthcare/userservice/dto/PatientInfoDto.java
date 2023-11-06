package com.healthcare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientInfoDto {

    private String patientId;

    private String fname;
    private String lname;
    private String email;
    private String address;

    private LocalDate dateOfBirth;
    private byte[] patient_image;

    private String mobile;
    private String gender;

    private String status;

    private LocalDateTime dateOfRegistration;
}
