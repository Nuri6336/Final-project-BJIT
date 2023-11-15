package com.healthcare.schedulingservice.controller;

import com.healthcare.schedulingservice.dto.ShiftDto;
import com.healthcare.schedulingservice.exception.ValueNotFoundException;
import com.healthcare.schedulingservice.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule/shift")
public class ShiftController {

    private final ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping
    public ResponseEntity<ShiftDto> createShift(@RequestBody ShiftDto shiftDto) {
        ShiftDto createdShift = shiftService.createShift(shiftDto);
        return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
    }

    @GetMapping("/{shiftId}")
    public ResponseEntity<ShiftDto> getShiftById(@PathVariable Long shiftId) {
        ShiftDto shiftDto = shiftService.getShiftById(shiftId);
        if (shiftDto != null) {
            return new ResponseEntity<>(shiftDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ShiftDto>> getAllShifts() {
        List<ShiftDto> allShifts = shiftService.getAllShifts();
        return new ResponseEntity<>(allShifts, HttpStatus.OK);
    }

    @PutMapping("/{shiftId}")
    public ResponseEntity<ShiftDto> updateShift(@PathVariable Long shiftId, @RequestBody ShiftDto shiftDto) {
        try {
            ShiftDto updatedShift = shiftService.updateShift(shiftId, shiftDto);
            return new ResponseEntity<>(updatedShift, HttpStatus.OK);
        } catch (ValueNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long shiftId) {
        shiftService.deleteShift(shiftId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

