package com.healthcare.decisionsupportservice.repository;

import com.healthcare.decisionsupportservice.entity.ExternalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalDataRepository extends JpaRepository<ExternalData, Long> {
}
