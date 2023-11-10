package com.healthcare.schedulingservice.service.implementation;

import com.healthcare.schedulingservice.dto.AppointmentDto;
import com.healthcare.schedulingservice.dto.ScheduleDto;
import com.healthcare.schedulingservice.entity.AppointmentEntity;
import com.healthcare.schedulingservice.entity.ScheduleEntity;
import com.healthcare.schedulingservice.repository.AppointmentRepository;
import com.healthcare.schedulingservice.repository.ScheduleRepository;
import com.healthcare.schedulingservice.service.ScheduleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleServiceImplementation implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    //As a doctor what to do
    @Override
    public String addSchedule(ScheduleDto scheduleDto, String doctorId){
        ScheduleEntity scheduleEntity = new ScheduleEntity();

        scheduleEntity.setDoctorId(doctorId);
        scheduleEntity.setScheduleDate(scheduleDto.getScheduleDate());
        scheduleEntity.setScheduleTime(scheduleDto.getScheduleTime());
        scheduleEntity.setAvailability(true);

        scheduleRepository.save(scheduleEntity);

        return "Schedule added to database.";
    }

    @Override
    public String editSchedule(Long scheduleId, String doctorId, ScheduleDto scheduleDto) throws Exception {
        ScheduleEntity scheduleEntity = scheduleRepository
                .findByScheduleIdAndDoctorId(scheduleId, doctorId)
                .orElseThrow(() -> new Exception("No value found."));

        scheduleEntity.setScheduleDate(scheduleDto.getScheduleDate());
        scheduleEntity.setScheduleTime(scheduleDto.getScheduleTime());
        scheduleEntity.setAvailability(true);

        scheduleRepository.save(scheduleEntity);

        return "Schedule updated to database.";
    }

    @Override
    public String deleteSchedule(Long scheduleId, String doctorId, ScheduleDto scheduleDto) throws Exception {
        ScheduleEntity scheduleEntity = scheduleRepository
                .findByScheduleIdAndDoctorId(scheduleId, doctorId)
                .orElseThrow(() -> new Exception("No value found."));

        scheduleRepository.delete(scheduleEntity);

        return "Schedule deleted from database.";
    }

    @Override
    public String changStatus(Long appointmentId, String doctorId) throws Exception {
        AppointmentEntity appointmentEntity = appointmentRepository
                .findByAppointmentIdAndDoctorId(appointmentId, doctorId)
                .orElseThrow(() -> new Exception("No value found."));

        appointmentEntity.setStatus("Done");

        return "Status change to done.";
    }

    @Override
    public List<AppointmentDto> doctorViewAppointment(String doctorId){
        List<AppointmentEntity> appointmentEntities = appointmentRepository
                .findByDoctorId(doctorId);

        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for (AppointmentEntity healthDataEntity1: appointmentEntities) {
            appointmentDtos.add(new ModelMapper().map(healthDataEntity1, AppointmentDto.class));
        }

        return appointmentDtos;
    }

    //As a patient what to do
    @Override
    public String bookAppointment(Long scheduleId, String patientId) throws Exception {
        Optional<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByScheduleIdAndAvailability(scheduleId, true);

        if (scheduleEntity.isPresent()){
            throw new Exception("Already booked");
        }

        ScheduleEntity scheduleEntity1 = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new Exception());

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setStatus("Booked");
        appointment.setScheduleEntity(scheduleEntity1);
        appointment.setDoctorId(scheduleEntity1.getDoctorId());
        appointment.setPatientId(patientId);

        appointmentRepository.save(appointment);

        return "Appointment booked for patientId " + patientId;
    }

    @Override
    public List<AppointmentDto> patientViewAppointment(String patientId){
        List<AppointmentEntity> appointmentEntities = appointmentRepository
                .findByPatientId(patientId);

        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for (AppointmentEntity healthDataEntity1: appointmentEntities) {
            appointmentDtos.add(new ModelMapper().map(healthDataEntity1, AppointmentDto.class));
        }

        return appointmentDtos;
    }

    //Common things to do
    @Override
    public List<ScheduleDto> viewSchedule(String doctorId){
        List<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByDoctorIdAndAvailability(doctorId, true);

        List<ScheduleDto> scheduleDtos = new ArrayList<>();

        for (ScheduleEntity scheduleEntity1: scheduleEntity) {
            scheduleDtos.add(new ModelMapper().map(scheduleEntity1, ScheduleDto.class));
        }

        return scheduleDtos;
    }
}
