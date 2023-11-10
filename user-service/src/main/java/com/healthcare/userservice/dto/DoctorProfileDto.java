package com.healthcare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfileDto {

    private String uniqueId;
    private String fname;
    private String lname;
    private String email;
    private String mobile;
    private String gender;

    private String address;
    private String age;
    private String professionalDescription;
    private String[] qualifications;
    private String[] specialities;
    private String doctorImage;
    private String roomNo;
}
