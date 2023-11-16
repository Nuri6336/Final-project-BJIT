package com.healthcare.userservice.controller;

import com.healthcare.userservice.dto.DoctorProfileDto;
import com.healthcare.userservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class SearchController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/by-name/{doctorName}")
    public ResponseEntity<?> viewProfileByDoctorName (@PathVariable String doctorName) {
        try {
            List<DoctorProfileDto> doctorProfileDtos = doctorService.findDoctorsByName(doctorName);
            return new ResponseEntity<>(doctorProfileDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/doctor/by-specialities/{speciality}")
    public ResponseEntity<?> viewProfileBySpeciality (@PathVariable String speciality) {
        try {
            List<DoctorProfileDto> doctorProfileDtos = doctorService.findDoctorsBySpecialty(speciality);
            return new ResponseEntity<>(doctorProfileDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
