package com.healthcare.userservice.repository;

import com.healthcare.userservice.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findById(Long patientId);
    Optional<PatientEntity> findByEmail(String email);
}
