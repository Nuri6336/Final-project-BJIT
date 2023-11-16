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
    private String doctorImage;

    private String address;
    private String age;
    private String professionalDescription;

    @ElementCollection
    private String[] qualifications;

    @ElementCollection
    private String[] specialities;
    private String roomNo;
    private String dutySlot;
}
