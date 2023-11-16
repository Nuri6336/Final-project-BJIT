package com.healthcare.schedulingservice.repository;

import com.healthcare.schedulingservice.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    Optional<AppointmentEntity> findByAppointmentIdAndDoctorId(Long appointmentId, String doctorId);
    List<AppointmentEntity> findByDoctorId(String doctorId);
    List<AppointmentEntity> findByPatientId(String patientId);
    Optional<AppointmentEntity> findByAppointmentIdAndPatientId(Long appointmentId, String currentUserId);
}
