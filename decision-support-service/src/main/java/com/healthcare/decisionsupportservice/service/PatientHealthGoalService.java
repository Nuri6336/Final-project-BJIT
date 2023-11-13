package com.healthcare.decisionsupportservice.service;

import com.healthcare.decisionsupportservice.dto.ExternalDataDto;
import com.healthcare.decisionsupportservice.entity.PatientHealthGoalEntity;
import com.healthcare.decisionsupportservice.entity.ProgressData;

import java.util.List;

public interface PatientHealthGoalService {

    PatientHealthGoalEntity createHealthGoal(PatientHealthGoalEntity healthGoal);

    List<PatientHealthGoalEntity> getHealthGoalsByPatientId(String patientId);

    PatientHealthGoalEntity updateHealthGoalProgress(Long goalId, String progressUpdate);

    void deleteHealthGoal(Long goalId);

    PatientHealthGoalEntity getHealthGoalDetails(Long goalId);

    List<ProgressData> getProgressData(Long goalId);

    void sendNotification(String patientId, String message);

    void updateProgressFromExternalSource(String patientId, ExternalDataDto data);
}

