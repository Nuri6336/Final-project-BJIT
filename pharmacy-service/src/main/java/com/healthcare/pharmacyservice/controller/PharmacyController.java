package com.healthcare.pharmacyservice.controller;

import com.healthcare.pharmacyservice.dto.PharmacyDto;
import com.healthcare.pharmacyservice.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @PostMapping("/pharmacy/add")
    public ResponseEntity<?> addMedicine (@RequestBody PharmacyDto pharmacyDto) {
        try {
            String response = pharmacyService.addMedicine(pharmacyDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pharmacy/update/{medicineId}")
    public ResponseEntity<?> updateMedicine (@PathVariable Long medicineId,@RequestBody PharmacyDto pharmacyDto) {
        try {
            String response = pharmacyService.updateMedicine(medicineId, pharmacyDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/pharmacy/delete/{medicineId}")
    public ResponseEntity<?> deleteMedicine (@PathVariable Long medicineId) {
        try {
            String response = pharmacyService.deleteMedicine(medicineId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pharmacy/view-individual-medicine/{medicineId}")
    public ResponseEntity<?> viewIndividualMedicine (@PathVariable Long medicineId) {
        try {
            PharmacyDto pharmacyDto = pharmacyService.viewIndividualMedicine(medicineId);
            return new ResponseEntity<>(pharmacyDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pharmacy/view-all-medicine")
    public ResponseEntity<?> viewAllMedicine () {
        try {
            List<PharmacyDto> pharmacyDtos = pharmacyService.viewAllMedicine();
            return new ResponseEntity<>(pharmacyDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pharmacy/view-all-medicine/below-expire-date")
    public ResponseEntity<?> viewAllMedicineWithValid () {
        try {
            List<PharmacyDto> pharmacyDtos = pharmacyService.findMedicinesNotExpired();
            return new ResponseEntity<>(pharmacyDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pharmacy/expired-medicines")
    public List<PharmacyDto> findAndSetExpiredMedicines() {
        return pharmacyService.findAndSetExpiredMedicines();
    }
}
