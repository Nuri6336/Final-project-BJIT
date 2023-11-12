package com.healthcare.schedulingservice.repository;

import com.healthcare.schedulingservice.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    Optional<ScheduleEntity> findByScheduleIdAndDoctorId(Long scheduleId, String doctorId);
    Optional<ScheduleEntity> findByScheduleIdAndAvailability(Long scheduleId, boolean availability);
    List<ScheduleEntity> findByDoctorIdAndAvailability(String doctorId, boolean availability);
    List<ScheduleEntity> findByDoctorId(String doctorId);
}
