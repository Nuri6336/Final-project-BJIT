package com.healthcare.userservice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEditDto {

    private String fname;
    private String lname;
    private String address;

    private LocalDate dateOfBirth;
    private byte[] patient_image;

    private String mobile;
    private String gender;
}
