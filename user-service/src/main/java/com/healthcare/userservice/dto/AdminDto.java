package com.healthcare.userservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {

    private String email;
    private String password;
    private String role;
}
