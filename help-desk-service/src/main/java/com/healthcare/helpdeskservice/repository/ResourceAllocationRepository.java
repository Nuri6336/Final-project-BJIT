package com.healthcare.helpdeskservice.repository;

import com.healthcare.helpdeskservice.entity.ResourceAllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocationEntity, Long> {

    @Query("SELECT ra FROM ResourceAllocationEntity ra " +
            "WHERE ra.resourceId = :resourceId " +
            "AND ((ra.startTime BETWEEN :startTime AND :endTime) OR (ra.endTime BETWEEN :startTime AND :endTime))")
    List<ResourceAllocationEntity> findOverlappingAllocations(
            @Param("resourceId") long resourceId,
            @Param("startTime") LocalDate startTime,
            @Param("endTime") LocalDate endTime
    );
}

