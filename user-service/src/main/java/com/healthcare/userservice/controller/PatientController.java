package com.healthcare.userservice.controller;

import com.healthcare.userservice.dto.PatientAddMoreInfoDto;
import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientProfileDto;
import com.healthcare.userservice.dto.UserDto;
import com.healthcare.userservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PutMapping("/patient/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody PatientEditDto patientEditDto) {
        try {
            String response = patientService.updatePatientInfo(patientEditDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/patient/profile")
    public ResponseEntity<?> viewProfile () {
        try {
            PatientProfileDto patientProfileDto = patientService.viewProfile();
            return new ResponseEntity<>(patientProfileDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
