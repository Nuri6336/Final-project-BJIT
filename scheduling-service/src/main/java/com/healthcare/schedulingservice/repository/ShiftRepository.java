package com.healthcare.schedulingservice.repository;

import com.healthcare.schedulingservice.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {
}
