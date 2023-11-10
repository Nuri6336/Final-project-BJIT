package com.healthcare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientProfileDto {

    private String uniqueId;
    private String fname;
    private String lname;
    private String email;
    private String mobile;
    private String gender;

    private String address;
    private String age;
    private String patientImage;
}
