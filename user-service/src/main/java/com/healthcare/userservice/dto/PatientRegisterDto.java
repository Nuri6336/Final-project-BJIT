package com.healthcare.userservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRegisterDto {

    private String fname;
    private String lname;
    private String email;
    private String mobile;
    private String gender;
    private String password;
    private String role;

    public void setAccessToken(String s) {
    }
}
