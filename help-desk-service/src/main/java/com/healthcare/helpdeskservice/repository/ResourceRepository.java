package com.healthcare.helpdeskservice.repository;

import com.healthcare.helpdeskservice.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
}
