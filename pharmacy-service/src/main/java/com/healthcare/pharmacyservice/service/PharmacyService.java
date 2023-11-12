package com.healthcare.pharmacyservice.service;

import com.healthcare.pharmacyservice.dto.PharmacyDto;
import com.healthcare.pharmacyservice.exception.ValueNotFoundException;

import java.util.List;

public interface PharmacyService {

    String addMedicine(PharmacyDto pharmacyDto);
    String updateMedicine(Long medicineId, PharmacyDto pharmacyDto) throws ValueNotFoundException;
    String deleteMedicine(Long medicineId) throws ValueNotFoundException;
    List<PharmacyDto> viewAllMedicine();
    PharmacyDto viewIndividualMedicine(Long medicineId) throws ValueNotFoundException;
}
