package com.healthcare.userservice.repository;

import com.healthcare.userservice.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    Optional<DoctorEntity> findByDoctorUniqueID(String uniqueId);
}
