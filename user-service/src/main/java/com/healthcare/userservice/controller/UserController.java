package com.healthcare.userservice.controller;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.UserDto;
import com.healthcare.userservice.dto.UserLoginRequestDto;
import com.healthcare.userservice.entity.DoctorEntity;
import com.healthcare.userservice.entity.PatientEntity;
import com.healthcare.userservice.repository.DoctorRepository;
import com.healthcare.userservice.repository.PatientRepository;
import com.healthcare.userservice.service.implementation.UserServiceImplementation;
import com.healthcare.userservice.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/users/userId/{userId}")
    public ResponseEntity<?> userDetailsByUserId(@PathVariable Long userId) {
        try {
            UserDto user = userServiceImplementation.getUserByUserId(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> register (@RequestBody UserDto userDto) {
        try {
            UserDto returnValue = userServiceImplementation.createUser(userDto);

            PatientEntity patientEntity = new PatientEntity();
            patientEntity.setPatientUniqueId(returnValue.getUniqueId());
            patientRepository.save(patientEntity);

            return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/register/doctor")
    public ResponseEntity<?> registerDoctor (@RequestBody UserDto userDto) {
        try {
            UserDto returnValue = userServiceImplementation.createDoctor(userDto);

            DoctorEntity doctorEntity = new DoctorEntity();
            doctorEntity.setDoctorUniqueID(returnValue.getUniqueId());
            doctorRepository.save(doctorEntity);

            return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
            UserDto userDto = userServiceImplementation.getUser(userLoginRequestDto.getEmail());
            List<String> userRoles = new ArrayList<>();
            userRoles.add(userDto.getRole());
            String accessToken = JWTUtils.generateToken(userDto.getEmail(), userRoles);
            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("userId", userDto.getUserId());
            loginResponse.put("email", userDto.getEmail());
            loginResponse.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + accessToken);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Wrong Credintial!", HttpStatus.UNAUTHORIZED);

        }
    }


    @GetMapping("/users/{email}")
    public ResponseEntity<?> userDetailsByEmail(@PathVariable String email) {
        try {
            UserDto user = userServiceImplementation.getUser(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/profile")
    public ResponseEntity<?> getUserProfile() {
        try {
            UserDto user = userServiceImplementation.getUserProfile();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/profile")
    public ResponseEntity<?> editUserProfile(@RequestBody UserDto userDto) {
        try {
            userServiceImplementation.editUserProfile(userDto);
            return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
