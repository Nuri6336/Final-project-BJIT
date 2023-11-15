package com.healthcare.schedulingservice.service;

import com.healthcare.schedulingservice.dto.AppointmentDto;
import com.healthcare.schedulingservice.dto.ResponseScheduleDto;
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
    String addScheduleWithShift(ScheduleDto scheduleDto) throws ValueNotFoundException, NameAlreadyExistsException;

    //As a patient what to do
    String bookAppointment(Long scheduleId) throws ValueNotFoundException, NameAlreadyExistsException;
    List<AppointmentDto> patientViewAppointment();

    //Common things to do
    List<ResponseScheduleDto> viewAvailableSchedule(String doctorId);
    List<ResponseScheduleDto> viewSchedule(String doctorId);


}
