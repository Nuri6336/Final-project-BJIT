package com.healthcare.pharmacyservice.service.implementation;

import com.healthcare.pharmacyservice.dto.PharmacyDto;
import com.healthcare.pharmacyservice.entity.PharmacyEntity;
import com.healthcare.pharmacyservice.exception.ValueNotFoundException;
import com.healthcare.pharmacyservice.repository.PharmacyRepository;
import com.healthcare.pharmacyservice.service.PharmacyService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PharmacyServiceImplementation implements PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Override
    public String addMedicine(PharmacyDto pharmacyDto){
        PharmacyEntity pharmacyEntity = new PharmacyEntity();

        pharmacyEntity.setMedicineType(pharmacyDto.getMedicineType());
        pharmacyEntity.setMedicineGenre(pharmacyDto.getMedicineGenre());
        pharmacyEntity.setMedicineName(pharmacyDto.getMedicineName());
        pharmacyEntity.setStatus(pharmacyDto.getStatus());
        pharmacyEntity.setExpireDate(pharmacyDto.getExpireDate());
        pharmacyEntity.setUnit(pharmacyDto.getUnit());

        pharmacyRepository.save(pharmacyEntity);

        return "Medicine added successfully.";
    }

    @Override
    public String updateMedicine(Long medicineId, PharmacyDto pharmacyDto) throws ValueNotFoundException {
        PharmacyEntity pharmacyEntity = pharmacyRepository.findById(medicineId)
                .orElseThrow(() -> new ValueNotFoundException("No medicine found."));

        pharmacyEntity.setMedicineType(pharmacyDto.getMedicineType());
        pharmacyEntity.setMedicineGenre(pharmacyDto.getMedicineGenre());
        pharmacyEntity.setMedicineName(pharmacyDto.getMedicineName());
        pharmacyEntity.setStatus(pharmacyDto.getStatus());
        pharmacyEntity.setExpireDate(pharmacyDto.getExpireDate());
        pharmacyEntity.setUnit(pharmacyDto.getUnit());

        pharmacyRepository.save(pharmacyEntity);

        return "Updated successfully.";
    }

    @Override
    public String deleteMedicine(Long medicineId) throws ValueNotFoundException {
        PharmacyEntity pharmacyEntity = pharmacyRepository.findById(medicineId)
                .orElseThrow(() -> new ValueNotFoundException("No medicine found."));

        pharmacyRepository.delete(pharmacyEntity);

        return "Medicine has been deleted successfully.";
    }

    @Override
    public List<PharmacyDto> viewAllMedicine(){
        List<PharmacyEntity> pharmacyEntities = pharmacyRepository.findAll();

        List<PharmacyDto> pharmacyDtos = new ArrayList<>();

        for (PharmacyEntity pharmacyEntity: pharmacyEntities) {
            pharmacyDtos.add(new ModelMapper().map(pharmacyEntity, PharmacyDto.class));
        }

        return pharmacyDtos;
    }

    @Override
    public PharmacyDto viewIndividualMedicine(Long medicineId) throws ValueNotFoundException {
        PharmacyEntity pharmacyEntity = pharmacyRepository.findById(medicineId)
                .orElseThrow(() -> new ValueNotFoundException("No medicine found."));

        return (new ModelMapper().map(pharmacyEntity, PharmacyDto.class));
    }
}
