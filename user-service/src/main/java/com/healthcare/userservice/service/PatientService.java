package com.healthcare.userservice.service;

import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientInfoDto;
import com.healthcare.userservice.dto.PatientRegisterDto;
import com.healthcare.userservice.exception.UserAlreadyExistsException;
import com.healthcare.userservice.exception.UserNotExistsException;
import org.springframework.security.core.userdetails.UserDetails;

public interface PatientService {

    String registerPatient(PatientRegisterDto patientRegisterDto) throws UserAlreadyExistsException;
    PatientInfoDto getPatientInfoById(Long id) throws UserNotExistsException;
    PatientInfoDto getUser(String email) throws UserNotExistsException;
    UserDetails loadUserByUsername(String email) throws UserNotExistsException;
    PatientInfoDto getUserProfile() throws UserNotExistsException;
    String editPatientProfile(PatientEditDto patientEditDto) throws UserNotExistsException;
    String deletePatientAccount() throws UserNotExistsException;
}
