package com.healthcare.userservice.service;

import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientInfoDto;
import com.healthcare.userservice.dto.PatientRegisterDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface PatientService {

    String registerPatient(PatientRegisterDto patientRegisterDto) throws Exception;
    PatientInfoDto getPatientInfoById(Long id) throws Exception;
    PatientInfoDto getUser(String email) throws Exception;
    UserDetails loadUserByUsername(String email) throws Exception;
    PatientInfoDto getUserProfile() throws Exception;
    String editPatientProfile(PatientEditDto patientEditDto) throws Exception;
}
