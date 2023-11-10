package com.healthcare.schedulingservice.service;

import com.healthcare.schedulingservice.dto.AppointmentDto;
import com.healthcare.schedulingservice.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {

    //As a doctor what to do
    String addSchedule(ScheduleDto scheduleDto, String doctorId);
    String editSchedule(Long scheduleId,String doctorId, ScheduleDto scheduleDto) throws Exception;
    String deleteSchedule(Long scheduleId, String doctorId, ScheduleDto scheduleDto) throws Exception;
    String changStatus(Long appointmentId, String doctorId) throws Exception;
    List<AppointmentDto> doctorViewAppointment(String doctorId);

    //As a patient what to do
    String bookAppointment(Long scheduleId, String patientId) throws Exception;
    List<AppointmentDto> patientViewAppointment(String patientId);

    //Common things to do
    List<ScheduleDto> viewSchedule(String doctorId);

}
