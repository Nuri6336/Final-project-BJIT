package com.healthcare.pharmacyservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String uniqueId;

    private String fname;
    private String lname;
    private String email;
    private String mobile;
    private String gender;
    private String password;
    private String role;
}
