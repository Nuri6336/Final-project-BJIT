package com.healthcare.helpdeskservice.repository;

import com.healthcare.helpdeskservice.entity.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQEntity, Long> {
}
