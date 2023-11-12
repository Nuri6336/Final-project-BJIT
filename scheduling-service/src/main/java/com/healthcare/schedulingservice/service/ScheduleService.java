package com.healthcare.schedulingservice.service;

import com.healthcare.schedulingservice.dto.AppointmentDto;
import com.healthcare.schedulingservice.dto.ScheduleDto;
import com.healthcare.schedulingservice.exception.NameAlreadyExistsException;
import com.healthcare.schedulingservice.exception.ValueNotFoundException;

import java.util.List;

public interface ScheduleService {

    //As a doctor what to do
    String addSchedule(ScheduleDto scheduleDto);
    String editSchedule(Long scheduleId, ScheduleDto scheduleDto) throws ValueNotFoundException;
    String deleteSchedule(Long scheduleId, ScheduleDto scheduleDto) throws ValueNotFoundException;
    String changStatus(Long appointmentId) throws ValueNotFoundException;
    List<AppointmentDto> doctorViewAppointment();

    //As a patient what to do
    String bookAppointment(Long scheduleId) throws ValueNotFoundException, NameAlreadyExistsException;
    List<AppointmentDto> patientViewAppointment();

    //Common things to do
    List<ScheduleDto> viewAvailableSchedule(String doctorId);
    List<ScheduleDto> viewSchedule(String doctorId);


}
