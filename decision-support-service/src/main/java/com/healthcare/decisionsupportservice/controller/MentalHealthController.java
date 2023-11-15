package com.healthcare.decisionsupportservice.controller;

import com.healthcare.decisionsupportservice.dto.MentalHealthExerciseDto;
import com.healthcare.decisionsupportservice.dto.MoodDto;
import com.healthcare.decisionsupportservice.exception.ExerciseNotFoundException;
import com.healthcare.decisionsupportservice.exception.MoodNotFoundException;
import com.healthcare.decisionsupportservice.exception.RecommendationNotFoundException;
import com.healthcare.decisionsupportservice.service.MentalHealthExerciseService;
import com.healthcare.decisionsupportservice.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/decision-support/mental-health")
public class MentalHealthController {

    @Autowired
    private MentalHealthExerciseService mentalHealthExerciseService;

    @Autowired
    private MoodService moodService;

    @PostMapping("/exercises")
    public ResponseEntity<?> createMentalHealthExercise(@RequestBody MentalHealthExerciseDto mentalHealthExerciseDTO) {
        try {
            mentalHealthExerciseService.createMentalHealthExercise(mentalHealthExerciseDTO);
            return new ResponseEntity<>("New exercise added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/exercises")
    public ResponseEntity<?> getMentalHealthExercise() {
        try {
            List<MentalHealthExerciseDto> mentalHealthExerciseDtoList = mentalHealthExerciseService.getAllMentalHealthExercise();
            if (mentalHealthExerciseDtoList.isEmpty()) {
                throw new ExerciseNotFoundException("No exercise is available.");
            }
            return new ResponseEntity<>(mentalHealthExerciseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mood-tracking")
    public ResponseEntity<?> createMood(@RequestBody MoodDto moodDTO) {
        try {
            moodService.createMood(moodDTO);
            return new ResponseEntity<>("New mood added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mood-tracking")
    public ResponseEntity<?> getMood() {
        try {
            List<MoodDto> moodDtoList = moodService.getMood();
            if (moodDtoList.isEmpty()) {
                throw new MoodNotFoundException("No mood track was found!!");
            }
            return new ResponseEntity<>(moodDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mood-tracking/last")
    public ResponseEntity<?> getLastMood() {
        try {
            Optional<MoodDto> moodDTO = moodService.getLastMood();
            if (moodDTO.isEmpty()) {
                throw new MoodNotFoundException("You've not entered any mood still now!");
            }
            return new ResponseEntity<>(moodDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendedExercises() throws Exception {
        try {
            List<MentalHealthExerciseDto> mentalHealthExerciseDtoList = moodService.getRecommendedExercises();
            if (mentalHealthExerciseDtoList.isEmpty()) {
                throw new RecommendationNotFoundException("There is no recommendation is for you.");
            }
            return new ResponseEntity<>(mentalHealthExerciseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}