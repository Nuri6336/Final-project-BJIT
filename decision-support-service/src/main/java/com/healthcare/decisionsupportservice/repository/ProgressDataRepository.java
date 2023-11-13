package com.healthcare.decisionsupportservice.repository;

import com.healthcare.decisionsupportservice.entity.ProgressData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressDataRepository extends JpaRepository<ProgressData, Long> {
    List<ProgressData> findByGoalId(Long goalId);
}