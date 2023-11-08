package com.healthcare.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String uniqueId;

    private String fname;
    private String lname;
    private String email;
    private String mobile;
    private String gender;
    private String password;
    private String role;

    private LocalDateTime dateOfRegistration;
}
