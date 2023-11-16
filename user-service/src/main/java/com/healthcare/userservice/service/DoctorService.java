package com.healthcare.userservice.service;

import com.healthcare.userservice.dto.DoctorAllocationDto;
import com.healthcare.userservice.dto.DoctorDto;
import com.healthcare.userservice.dto.DoctorProfileDto;

import java.util.List;

public interface DoctorService {

    String updateDoctorInfo(DoctorDto doctorDto) throws Exception;
    DoctorProfileDto viewProfile() throws Exception;
    String addOrUpdateRoomAllocation(String doctorId, DoctorAllocationDto doctorAllocationDto);
    List<DoctorProfileDto> findDoctorsByName(String doctorName) throws Exception;
    List<DoctorProfileDto> findDoctorsBySpecialty(String specialty);
    DoctorProfileDto viewProfileByUniqueId(String doctorId) throws Exception;
}
