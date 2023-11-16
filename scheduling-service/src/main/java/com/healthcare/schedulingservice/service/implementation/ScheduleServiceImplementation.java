package com.healthcare.schedulingservice.service.implementation;

import com.healthcare.schedulingservice.constants.AppConstants;
import com.healthcare.schedulingservice.dto.*;
import com.healthcare.schedulingservice.entity.AppointmentEntity;
import com.healthcare.schedulingservice.entity.ScheduleEntity;
import com.healthcare.schedulingservice.entity.ShiftEntity;
import com.healthcare.schedulingservice.exception.NameAlreadyExistsException;
import com.healthcare.schedulingservice.exception.ValueNotFoundException;
import com.healthcare.schedulingservice.networkmanager.NotificationFeingClient;
import com.healthcare.schedulingservice.networkmanager.UserFeingClient;
import com.healthcare.schedulingservice.repository.AppointmentRepository;
import com.healthcare.schedulingservice.repository.ScheduleRepository;
import com.healthcare.schedulingservice.repository.ShiftRepository;
import com.healthcare.schedulingservice.service.ScheduleService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Autowired
    private NotificationFeingClient notificationFeingClient;
    @Autowired
    private ShiftRepository shiftRepository;

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
    public String deleteSchedule(Long scheduleId) throws ValueNotFoundException {
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

        ScheduleEntity scheduleEntity = appointmentEntity.getScheduleEntity();

        if (scheduleEntity == null) {
            // Handle the case where the appointment is not associated with a schedule
            return "Appointment is not associated with a schedule.";
        }

        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime scheduleEndTime = LocalDateTime.of(
                scheduleEntity.getScheduleDate(),
                scheduleEntity.getScheduleTime().plusMinutes(30));

        if (currentDateTime.isAfter(scheduleEndTime)) {
            // Change the status to "Done" only if the current time is after the schedule end time
            appointmentEntity.setStatus("Done");

            // Save the updated appointment status to the database
            appointmentRepository.save(appointmentEntity);

            return "Status changed to Done.";
        } else {
            return "Cannot change status until after the schedule end time.";
        }
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
    public String bookAppointment(Long scheduleId) throws NameAlreadyExistsException, ValueNotFoundException {
        ScheduleEntity scheduleEntity = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(() -> new ValueNotFoundException("Use a valid id to book an appointment"));

        if (!scheduleEntity.isAvailability()) {
            throw new NameAlreadyExistsException("Already booked by another patient.");
        }

        // Check if the schedule's date and time are in the future
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime scheduleDateTime = LocalDateTime.of(scheduleEntity.getScheduleDate(), scheduleEntity.getScheduleTime());

        if (currentDateTime.isAfter(scheduleDateTime)) {
            return "Cannot book an appointment for past time.";
        }

        scheduleEntity.setAvailability(false);

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setStatus("Booked");
        appointment.setScheduleEntity(scheduleEntity);
        appointment.setDoctorId(scheduleEntity.getDoctorId());
        appointment.setPatientId(getCurrentUserId());

        appointmentRepository.save(appointment);

        NotificationDto notificationRequest = new NotificationDto();
        notificationRequest.setMessage("Appointment booked for patientId " + getCurrentUserId());
        notificationRequest.setNotificationType("Appointment");
        notificationRequest.setStatus("UNREAD");
        notificationRequest.setRecipientId(scheduleEntity.getDoctorId());

        try {
            notificationFeingClient.sendNotification(notificationRequest);
        } catch (FeignException e) {
            return "Feign client exception " + e.getMessage();
        }

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
    public List<ResponseScheduleDto> viewAvailableSchedule(String doctorId){
        List<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByDoctorIdAndAvailability(doctorId, true);

        List<ResponseScheduleDto> scheduleDtos = new ArrayList<>();

        for (ScheduleEntity scheduleEntity1: scheduleEntity) {
            scheduleDtos.add(new ModelMapper().map(scheduleEntity1, ResponseScheduleDto.class));
        }

        return scheduleDtos;
    }

    @Override
    public List<ResponseScheduleDto> viewSchedule(String doctorId){
        List<ScheduleEntity> scheduleEntity = scheduleRepository
                .findByDoctorId(doctorId);

        List<ResponseScheduleDto> scheduleDtos = new ArrayList<>();

        for (ScheduleEntity scheduleEntity1: scheduleEntity) {
            scheduleDtos.add(new ModelMapper().map(scheduleEntity1, ResponseScheduleDto.class));
        }

        return scheduleDtos;
    }

    //Schedule with shift
    @Override
    public String addScheduleWithShift(ScheduleDto scheduleDto) throws ValueNotFoundException, NameAlreadyExistsException {
        String doctorId = getCurrentUserId();
        LocalDateTime scheduleDateTime = LocalDateTime.of(scheduleDto.getScheduleDate(), scheduleDto.getScheduleTime());

        ShiftEntity shift = shiftRepository.findById(scheduleDto.getShiftId())
                .orElseThrow(() -> new ValueNotFoundException("Shift not found"));

        LocalDateTime currentDateTime = LocalDateTime.now();
        if (scheduleDateTime.isBefore(currentDateTime)) {
            throw new IllegalArgumentException("Schedule time cannot be in the past");
        }

        List<ScheduleEntity> scheduleEntries = new ArrayList<>();

        // Assuming 30 minutes duration for each schedule entry
        while (scheduleDateTime.isBefore(scheduleDto.getScheduleDate().atTime(shift.getEndTime()))) {
            ScheduleEntity scheduleEntity = new ScheduleEntity();
            scheduleEntity.setDoctorId(doctorId);
            scheduleEntity.setShiftId(shift);
            scheduleEntity.setScheduleDate(scheduleDateTime.toLocalDate());
            scheduleEntity.setScheduleTime(scheduleDateTime.toLocalTime());
            scheduleEntity.setAvailability(true);

            scheduleEntries.add(scheduleEntity);

            // Move to the next schedule time
            scheduleDateTime = scheduleDateTime.plusMinutes(30); // Adjust duration as needed
        }

        for (ScheduleEntity scheduleEntry : scheduleEntries) {
            List<ScheduleEntity> existingSchedules = scheduleRepository.findByDoctorIdAndShiftIdAndScheduleDate(
                    doctorId, shift, scheduleEntry.getScheduleDate());

            if (!existingSchedules.isEmpty()) {
                throw new NameAlreadyExistsException("You already booked this time.");
            }
        }

        scheduleRepository.saveAll(scheduleEntries);

        return "Schedules added to the database.";
    }

    @Override
    public String cancelAppointment(Long appointmentId) throws ValueNotFoundException {
        AppointmentEntity appointmentEntity = appointmentRepository
                .findByAppointmentIdAndPatientId(appointmentId, getCurrentUserId())
                .orElseThrow(() -> new ValueNotFoundException("Invalid appointment id"));


        ScheduleEntity scheduleEntity = appointmentEntity.getScheduleEntity();

        if (scheduleEntity != null) {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);

            LocalDateTime scheduleDateTime = LocalDateTime.of(scheduleEntity.getScheduleDate(), scheduleEntity.getScheduleTime());

            // Check if the schedule time is more than 6 hours from the current time
            if (currentDateTime.plusHours(6).isAfter(scheduleDateTime)) {
                return "Cannot cancel appointment. Less than 6 hours remaining.";
            }

            // Set the availability status back to true in the schedule table
            scheduleEntity.setAvailability(true);
            // Update the schedule in the database
            scheduleRepository.save(scheduleEntity);
        }

        appointmentRepository.delete(appointmentEntity);

        // Optionally, you can send a notification or perform other actions here

        return "Appointment canceled successfully.";
    }



}
