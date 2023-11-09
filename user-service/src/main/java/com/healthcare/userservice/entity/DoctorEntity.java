package com.healthcare.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name="doctor")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorUniqueID;

    @Lob
    private byte[] doctorImage;

    private String address;
    private String age;
    private String professionalDescription;
    private String[] qualifications;
    private String[] specialities;
    private String roomNo;
}
