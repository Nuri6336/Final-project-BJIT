package com.healthcare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEditDto {


    private String address;
    private String age;
    private String patientImage;

    private String fname;
    private String lname;
    private String mobile;
    private String gender;
}
