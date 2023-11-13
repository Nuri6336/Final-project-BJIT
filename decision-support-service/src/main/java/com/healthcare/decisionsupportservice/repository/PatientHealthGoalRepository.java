package com.healthcare.decisionsupportservice.repository;

import com.healthcare.decisionsupportservice.entity.PatientHealthGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientHealthGoalRepository extends JpaRepository<PatientHealthGoalEntity, Long> {
    List<PatientHealthGoalEntity> findByPatientId(String patientId);
}
