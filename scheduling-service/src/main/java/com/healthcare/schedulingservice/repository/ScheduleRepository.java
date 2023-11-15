package com.healthcare.schedulingservice.repository;

import com.healthcare.schedulingservice.entity.ScheduleEntity;
import com.healthcare.schedulingservice.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    Optional<ScheduleEntity> findByScheduleIdAndDoctorId(Long scheduleId, String doctorId);
    Optional<ScheduleEntity> findByScheduleIdAndAvailability(Long scheduleId, boolean availability);
    List<ScheduleEntity> findByDoctorIdAndAvailability(String doctorId, boolean availability);
    List<ScheduleEntity> findByDoctorId(String doctorId);

    List<ScheduleEntity> findByDoctorIdAndShiftIdAndScheduleDate(String doctorId, ShiftEntity shift, LocalDate scheduleDate);
}
