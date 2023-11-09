package com.healthcare.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDto {

    private String address;
    private String age;
    private String professionalDescription;
    private String[] qualifications;
    private String[] specialities;
    private byte[] doctorImage;
}
