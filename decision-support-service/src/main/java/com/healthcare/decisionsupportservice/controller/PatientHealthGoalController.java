package com.healthcare.decisionsupportservice.controller;

import com.healthcare.decisionsupportservice.dto.ExternalDataDto;
import com.healthcare.decisionsupportservice.dto.PatientHealthGoalDto;
import com.healthcare.decisionsupportservice.entity.PatientHealthGoalEntity;
import com.healthcare.decisionsupportservice.entity.ProgressData;
import com.healthcare.decisionsupportservice.exception.ValueNotFoundException;
import com.healthcare.decisionsupportservice.service.PatientHealthGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decision-support")
public class PatientHealthGoalController {

    @Autowired
    private PatientHealthGoalService patientHealthGoalService;

    @PostMapping("/goal/create")
    public ResponseEntity<PatientHealthGoalEntity> createHealthGoal(@RequestBody PatientHealthGoalDto patientHealthGoalDto) {
        PatientHealthGoalEntity createdGoal = patientHealthGoalService.createHealthGoal(patientHealthGoalDto);
        return ResponseEntity.ok(createdGoal);
    }

    @GetMapping("/goal/by-patient/{patientId}")
    public ResponseEntity<List<PatientHealthGoalEntity>> getHealthGoalsByPatientId(@PathVariable String patientId) {
        List<PatientHealthGoalEntity> healthGoals = patientHealthGoalService.getHealthGoalsByPatientId(patientId);
        return ResponseEntity.ok(healthGoals);
    }

    @PutMapping("/goal/update-progress/{goalId}/{goalValue}")
    public ResponseEntity<PatientHealthGoalEntity> updateHealthGoalProgress(
            @PathVariable Long goalId, @PathVariable double goalValue) throws ValueNotFoundException {
        PatientHealthGoalEntity updatedGoal = patientHealthGoalService.updateHealthGoalProgress(goalId, goalValue);
        return ResponseEntity.ok(updatedGoal);
    }

    @DeleteMapping("/goal/delete/{goalId}")
    public ResponseEntity<Void> deleteHealthGoal(@PathVariable Long goalId) {
        patientHealthGoalService.deleteHealthGoal(goalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/goal/details/{goalId}")
    public ResponseEntity<PatientHealthGoalEntity> getHealthGoalDetails(@PathVariable Long goalId) {
        PatientHealthGoalEntity goalDetails = patientHealthGoalService.getHealthGoalDetails(goalId);
        return ResponseEntity.ok(goalDetails);
    }

    @GetMapping("/goal/progress-data/{goalId}")
    public ResponseEntity<List<ProgressData>> getProgressData(@PathVariable Long goalId) {
        List<ProgressData> progressData = patientHealthGoalService.getProgressData(goalId);
        return ResponseEntity.ok(progressData);
    }

    @PostMapping("/goal/send-notification/{patientId}")
    public ResponseEntity<Void> sendNotification(@PathVariable String patientId, @RequestBody String message) {
        patientHealthGoalService.sendNotification(patientId, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/goal/update-external-progress/{patientId}")
    public ResponseEntity<Void> updateProgressFromExternalSource(
            @PathVariable String patientId, @RequestBody ExternalDataDto data) {
        patientHealthGoalService.updateProgressFromExternalSource(patientId, data);
        return ResponseEntity.ok().build();
    }
}

