package com.healthcare.schedulingservice.service.implementation;

import com.healthcare.schedulingservice.constants.AppConstants;
import com.healthcare.schedulingservice.dto.AppointmentDto;
import com.healthcare.schedulingservice.dto.ScheduleDto;
import com.healthcare.schedulingservice.dto.UserDto;
import com.healthcare.schedulingservice.entity.AppointmentEntity;
import com.healthcare.schedulingservice.entity.ScheduleEntity;
import com.healthcare.schedulingservice.exception.NameAlreadyExistsException;
import com.healthcare.schedulingservice.exception.ValueNotFoundException;
import com.healthcare.schedulingservice.networkmanager.UserFeingClient;
import com.healthcare.schedulingservice.repository.AppointmentRepository;
import com.healthcare.schedulingservice.repository.ScheduleRepository;
import com.healthcare.schedulingservice.service.ScheduleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserFeingClient userFeingClient;

    private UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        return userFeingClient.userDetailsByEmail(userMail);
    }

    private String getCurrentUserId() {
        return getCurrentUser().getUniqueId();
    }

    //As a doctor what to do
    @Override
    public String addSchedule(ScheduleDto scheduleDto){
        ScheduleEntity scheduleEntity = new ScheduleEntity();

        scheduleEntity.setDoctorId(getCurrentUserId());
        scheduleEntity.setScheduleDate(scheduleDto.getScheduleDate());
        scheduleEntity.setScheduleTime(scheduleDto.getScheduleTime());
        scheduleEntity.setAvailability(true);

        scheduleRepository.save(scheduleEntity);

        return "Schedule added to database.";
    }

    @Override
    public String editSchedule(Long scheduleId, ScheduleDto scheduleDto) throws ValueNotFoundException {
        ScheduleEntity scheduleEntity = scheduleRepository
                .findByScheduleIdAndDoctorId(scheduleId, getCurrentUserId())
                .orElseThrow(() -> new ValueNotFoundException(AppConstants.USER_NOT_FOUND));

        scheduleEntity.setScheduleDate(scheduleDto.getScheduleDate());
        scheduleEntity.setScheduleTime(scheduleDto.getScheduleTime());
        scheduleEntity.setAvailability(true);

        scheduleRepository.save(scheduleEntity);

        return "Schedule updated to database.";
    }

    @Override
    public String deleteSchedule(Long scheduleId, ScheduleDto scheduleDto) throws ValueNotFoundException {
        ScheduleEntity scheduleEntity = scheduleRepository
                .findByScheduleIdAndDoctorId(scheduleId, getCurrentUserId())
                .orElseThrow(() -> new ValueNotFoundException(AppConstants.USER_NOT_FOUND));

        scheduleRepository.delete(scheduleEntity);

        return "Schedule deleted from database.";
    }

    @Override
    public String changStatus(Long appointmentId) throws ValueNotFoundException {
        AppointmentEntity appointmentEntity = appointmentRepository
                .findByAppointmentIdAndDoctorId(appointmentId, getCurrentUserId())
                .orElseThrow(() -> new ValueNotFoundException(AppConstants.USER_NOT_FOUND));

        appointmentEntity.setStatus("Done");

        return "Status change to done.";
    }

    @Override
    public List<AppointmentDto> doctorViewAppointment(){
        List<AppointmentEntity> appointmentEntities = appointmentRepository
                .findByDoctorId(getCurrentUserId());

        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for (AppointmentEntity appointmentEntity: appointmentEntities) {
            appointmentDtos.add(new ModelMapper().map(appointmentEntity, AppointmentDto.class));
        }

        return appointmentDtos;
    }

    //As a patient what to do
    @Override
    public String bookAppointment(Long scheduleId) throws NameAlreadyExistsException,ValueNotFoundException {
        Optional<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByScheduleIdAndAvailability(scheduleId, false);

        if (scheduleEntity.isPresent()){
            throw new NameAlreadyExistsException("Already booked by another patient.");
        }

        ScheduleEntity scheduleEntity1 = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new ValueNotFoundException("Use valid id to book appointment"));

        scheduleEntity1.setAvailability(false);

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setStatus("Booked");
        appointment.setScheduleEntity(scheduleEntity1);
        appointment.setDoctorId(scheduleEntity1.getDoctorId());
        appointment.setPatientId(getCurrentUserId());

        appointmentRepository.save(appointment);

        return "Appointment booked for patientId " + getCurrentUserId();
    }

    @Override
    public List<AppointmentDto> patientViewAppointment(){
        List<AppointmentEntity> appointmentEntities = appointmentRepository
                .findByPatientId(getCurrentUserId());

        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for (AppointmentEntity healthDataEntity1: appointmentEntities) {
            appointmentDtos.add(new ModelMapper().map(healthDataEntity1, AppointmentDto.class));
        }

        return appointmentDtos;
    }

    //Common things to do
    @Override
    public List<ScheduleDto> viewAvailableSchedule(String doctorId){
        List<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByDoctorIdAndAvailability(doctorId, true);

        List<ScheduleDto> scheduleDtos = new ArrayList<>();

        for (ScheduleEntity scheduleEntity1: scheduleEntity) {
            scheduleDtos.add(new ModelMapper().map(scheduleEntity1, ScheduleDto.class));
        }

        return scheduleDtos;
    }

    @Override
    public List<ScheduleDto> viewSchedule(String doctorId){
        List<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByDoctorId(doctorId);

        List<ScheduleDto> scheduleDtos = new ArrayList<>();

        for (ScheduleEntity scheduleEntity1: scheduleEntity) {
            scheduleDtos.add(new ModelMapper().map(scheduleEntity1, ScheduleDto.class));
        }

        return scheduleDtos;
    }
}
