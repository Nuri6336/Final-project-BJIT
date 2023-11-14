package com.healthcare.pharmacyservice.repository;

import com.healthcare.pharmacyservice.entity.PharmacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PharmacyRepository extends JpaRepository<PharmacyEntity, Long> {
    List<PharmacyEntity> findByExpireDateGreaterThan(LocalDate currentDate);

    List<PharmacyEntity> findByExpireDateLessThan(LocalDate currentDate);
}
