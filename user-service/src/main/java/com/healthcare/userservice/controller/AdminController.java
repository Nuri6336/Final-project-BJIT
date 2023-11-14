package com.healthcare.userservice.controller;

import com.healthcare.userservice.dto.DoctorAllocationDto;
import com.healthcare.userservice.dto.DoctorDto;
import com.healthcare.userservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PutMapping("/doctor/update-allocation/{doctorId}")
    public ResponseEntity<?> updateAllocatedRoom(@PathVariable String doctorId,
            @RequestBody DoctorAllocationDto doctorAllocationDto) {
        try {
            String response = doctorService.addOrUpdateRoomAllocation(doctorId, doctorAllocationDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
