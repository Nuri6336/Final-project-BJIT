package com.healthcare.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name="patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String patientId;

    private String fname;
    private String lname;
    private String email;
    private String address;

    private LocalDate dateOfBirth;
    @Lob
    private byte[] patient_image;

    private String mobile;
    private String gender;

    private String status;
    private String password;

    private LocalDateTime dateOfRegistration;
    private String role;
}
