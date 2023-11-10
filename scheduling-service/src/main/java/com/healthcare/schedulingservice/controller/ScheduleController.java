package com.healthcare.schedulingservice.controller;

import com.healthcare.schedulingservice.dto.ScheduleDto;
import com.healthcare.schedulingservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/add/{doctorId}")
    public ResponseEntity<?> addSchedule (@RequestParam String doctorId, @RequestBody ScheduleDto scheduleDto) {
        try {
            String response = scheduleService.addSchedule(scheduleDto,doctorId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
