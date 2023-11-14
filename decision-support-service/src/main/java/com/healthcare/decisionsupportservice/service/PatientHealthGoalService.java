package com.healthcare.decisionsupportservice.service;

import com.healthcare.decisionsupportservice.dto.ExternalDataDto;
import com.healthcare.decisionsupportservice.dto.PatientHealthGoalDto;
import com.healthcare.decisionsupportservice.entity.PatientHealthGoalEntity;
import com.healthcare.decisionsupportservice.entity.ProgressData;
import com.healthcare.decisionsupportservice.exception.ValueNotFoundException;

import java.util.List;

public interface PatientHealthGoalService {

    PatientHealthGoalEntity createHealthGoal(PatientHealthGoalDto patientHealthGoalDto);

    List<PatientHealthGoalEntity> getHealthGoalsByPatientId(String patientId);

    PatientHealthGoalEntity updateHealthGoalProgress(Long goalId, double updateValue) throws ValueNotFoundException;

    void deleteHealthGoal(Long goalId);

    PatientHealthGoalEntity getHealthGoalDetails(Long goalId);

    List<ProgressData> getProgressData(Long goalId);

    void sendNotification(String patientId, String message);

    void updateProgressFromExternalSource(String patientId, ExternalDataDto data);
}

