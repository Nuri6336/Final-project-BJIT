package com.healthcare.userservice.service;

import com.healthcare.userservice.dto.PatientAddMoreInfoDto;
import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientProfileDto;
import com.healthcare.userservice.exception.UserNotExistsException;

public interface PatientService {

    String updatePatientInfo(PatientEditDto patientEditDto) throws Exception;
    PatientProfileDto viewProfile() throws Exception;
}
