package com.healthcare.userservice.controller;

import com.healthcare.userservice.dto.DoctorDto;
import com.healthcare.userservice.dto.DoctorProfileDto;
import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientProfileDto;
import com.healthcare.userservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PutMapping("/doctor/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody DoctorDto doctorDto) {
        try {
            String response = doctorService.updateDoctorInfo(doctorDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/doctor/profile")
    public ResponseEntity<?> viewProfile () {
        try {
            DoctorProfileDto doctorProfileDto = doctorService.viewProfile();
            return new ResponseEntity<>(doctorProfileDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/doctor/profile/{doctorId}")
    public ResponseEntity<?> viewProfileByDoctorId (@PathVariable String doctorId) {
        try {
            DoctorProfileDto doctorProfileDto = doctorService.viewProfileByUniqueId(doctorId);
            return new ResponseEntity<>(doctorProfileDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
