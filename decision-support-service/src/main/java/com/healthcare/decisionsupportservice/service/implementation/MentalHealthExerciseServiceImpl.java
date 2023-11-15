package com.healthcare.decisionsupportservice.service.implementation;

import com.healthcare.decisionsupportservice.dto.MentalHealthExerciseDto;
import com.healthcare.decisionsupportservice.entity.MentalHealthExerciseEntity;
import com.healthcare.decisionsupportservice.repository.MentalHealthExerciseRepository;
import com.healthcare.decisionsupportservice.service.MentalHealthExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MentalHealthExerciseServiceImpl implements MentalHealthExerciseService {

    @Autowired
    private MentalHealthExerciseRepository mentalHealthExerciseRepository;

    @Override
    public void createMentalHealthExercise(MentalHealthExerciseDto mentalHealthExerciseDTO) {
        MentalHealthExerciseEntity mentalHealthExerciseEntity = new ModelMapper().map(mentalHealthExerciseDTO, MentalHealthExerciseEntity.class);
        mentalHealthExerciseRepository.save(mentalHealthExerciseEntity);
    }

    @Override
    public List<MentalHealthExerciseDto> getAllMentalHealthExercise() {
        List<MentalHealthExerciseEntity> mentalHealthExerciseEntities = mentalHealthExerciseRepository.findAll();
        List<MentalHealthExerciseDto> mentalHealthExerciseDtoList = new ArrayList<>();
        for (MentalHealthExerciseEntity mentalHealthExerciseEntity : mentalHealthExerciseEntities) {
            mentalHealthExerciseDtoList.add(new ModelMapper()
                    .map(mentalHealthExerciseEntity, MentalHealthExerciseDto.class));
        }
        return mentalHealthExerciseDtoList;
    }
}
