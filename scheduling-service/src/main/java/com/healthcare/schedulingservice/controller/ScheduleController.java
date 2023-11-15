package com.healthcare.schedulingservice.controller;

import com.healthcare.schedulingservice.dto.AppointmentDto;
import com.healthcare.schedulingservice.dto.ResponseScheduleDto;
import com.healthcare.schedulingservice.dto.ScheduleDto;
import com.healthcare.schedulingservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/schedule/add")
    public ResponseEntity<?> addSchedule ( @RequestBody ScheduleDto scheduleDto) {
        try {
            String response = scheduleService.addSchedule(scheduleDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/schedule/book-appointment/{scheduleId}")
    public ResponseEntity<?> bookAppointment ( @PathVariable Long scheduleId) {
        try {
            String response = scheduleService.bookAppointment(scheduleId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/schedule/change-status/{appointmentId}")
    public ResponseEntity<?> changeStatus ( @PathVariable Long appointmentId) {
        try {
            String response = scheduleService.changStatus(appointmentId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/schedule/doctor/view-appointment")
    public ResponseEntity<?> doctorViewAppointment () {
        try {
            List<AppointmentDto> appointmentDtos = scheduleService.doctorViewAppointment();
            return new ResponseEntity<>(appointmentDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/schedule/patient/view-appointment")
    public ResponseEntity<?> patientViewAppointment () {
        try {
            List<AppointmentDto> appointmentDtos = scheduleService.patientViewAppointment();
            return new ResponseEntity<>(appointmentDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/schedule/view-schedule/{doctorId}")
    public ResponseEntity<?> viewSchedule (@PathVariable String doctorId) {
        try {
            List<ResponseScheduleDto> scheduleDtos = scheduleService.viewAvailableSchedule(doctorId);
            return new ResponseEntity<>(scheduleDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/schedule/view-all-schedule/{doctorId}")
    public ResponseEntity<?> viewAllSchedule (@PathVariable String doctorId) {
        try {
            List<ResponseScheduleDto> scheduleDtos = scheduleService.viewSchedule(doctorId);
            return new ResponseEntity<>(scheduleDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/schedule/add-with-shift")
    public ResponseEntity<?> addScheduleWithShift ( @RequestBody ScheduleDto scheduleDto) {
        try {
            String response = scheduleService.addScheduleWithShift(scheduleDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
