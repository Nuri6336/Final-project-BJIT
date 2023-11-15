package com.healthcare.schedulingservice.service;

import com.healthcare.schedulingservice.dto.ShiftDto;
import com.healthcare.schedulingservice.exception.ValueNotFoundException;

import java.util.List;

public interface ShiftService {

    ShiftDto createShift(ShiftDto shiftDto);
    ShiftDto getShiftById(Long shiftId);
    List<ShiftDto> getAllShifts();
    ShiftDto updateShift(Long shiftId, ShiftDto shiftDto) throws ValueNotFoundException;
    void deleteShift(Long shiftId);
}
