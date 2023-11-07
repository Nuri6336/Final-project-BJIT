package com.healthcare.userservice.controller;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.PatientInfoDto;
import com.healthcare.userservice.dto.PatientRegisterDto;
import com.healthcare.userservice.dto.UserLoginDto;
import com.healthcare.userservice.service.PatientService;
import com.healthcare.userservice.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/patient/register")
    public ResponseEntity<?> register (@RequestBody PatientRegisterDto patientRegisterDto) {
        try {
            String response = patientService.registerPatient(patientRegisterDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/patient/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
            PatientInfoDto patientInfoDto = patientService.getUser(userLoginDto.getEmail());
            List<String> userRoles = new ArrayList<>();
            userRoles.add(AppConstants.ROLE_PATIENT);

            String accessToken = JWTUtils.generateToken(patientInfoDto.getEmail(), userRoles);
            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("userId", patientInfoDto.getPatientId());
            loginResponse.put("email", patientInfoDto.getEmail());
            loginResponse.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + accessToken);

            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

        }
        catch (Exception e) {
            return new ResponseEntity<>("Wrong Credential!", HttpStatus.UNAUTHORIZED);

        }
    }
}
