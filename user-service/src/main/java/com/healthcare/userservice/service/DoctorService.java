package com.healthcare.userservice.service;

import com.healthcare.userservice.dto.DoctorDto;
import com.healthcare.userservice.dto.DoctorProfileDto;

public interface DoctorService {

    String updateDoctorInfo(DoctorDto doctorDto) throws Exception;
    DoctorProfileDto viewProfile() throws Exception;
}
