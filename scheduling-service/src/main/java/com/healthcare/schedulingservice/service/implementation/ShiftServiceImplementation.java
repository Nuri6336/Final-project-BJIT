package com.healthcare.schedulingservice.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.healthcare.schedulingservice.dto.ShiftDto;
import com.healthcare.schedulingservice.entity.ShiftEntity;
import com.healthcare.schedulingservice.exception.ValueNotFoundException;
import com.healthcare.schedulingservice.repository.ShiftRepository;
import com.healthcare.schedulingservice.service.ShiftService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShiftServiceImplementation implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftServiceImplementation(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {
        ShiftEntity shiftEntity = new ModelMapper().map(shiftDto, ShiftEntity.class);
        ShiftEntity savedShift = shiftRepository.save(shiftEntity);
        return new ModelMapper().map(savedShift, ShiftDto.class);
    }

    @Override
    public ShiftDto getShiftById(Long shiftId) {
        Optional<ShiftEntity> shiftOptional = shiftRepository.findById(shiftId);
        return shiftOptional.map(shiftEntity -> new ModelMapper().map(shiftEntity, ShiftDto.class)).orElse(null);
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        List<ShiftEntity> shiftEntities = shiftRepository.findAll();
        return shiftEntities.stream()
                .map(shiftEntity -> new ModelMapper().map(shiftEntity, ShiftDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ShiftDto updateShift(Long shiftId, ShiftDto shiftDto) throws ValueNotFoundException {
        Optional<ShiftEntity> existingShiftOptional = shiftRepository.findById(shiftId);
        if (existingShiftOptional.isPresent()) {
            ShiftEntity existingShift = existingShiftOptional.get();
            // Update fields with non-null values from shiftDto
            // Handle mapping of null values appropriately based on your requirements
            new ModelMapper().map(shiftDto, existingShift);
            ShiftEntity updatedShift = shiftRepository.save(existingShift);
            return new ModelMapper().map(updatedShift, ShiftDto.class);
        } else {
            throw new ValueNotFoundException("Shift with id " + shiftId + " not found");
        }
    }

    @Override
    public void deleteShift(Long shiftId) {
        shiftRepository.deleteById(shiftId);
    }
}
