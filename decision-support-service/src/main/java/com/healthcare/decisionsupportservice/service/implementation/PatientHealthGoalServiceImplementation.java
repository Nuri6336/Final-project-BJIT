package com.healthcare.decisionsupportservice.service.implementation;

import com.healthcare.decisionsupportservice.dto.ExternalDataDto;
import com.healthcare.decisionsupportservice.entity.PatientHealthGoalEntity;
import com.healthcare.decisionsupportservice.entity.ProgressData;
import com.healthcare.decisionsupportservice.repository.PatientHealthGoalRepository;
import com.healthcare.decisionsupportservice.repository.ProgressDataRepository;
import com.healthcare.decisionsupportservice.service.PatientHealthGoalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientHealthGoalServiceImplementation implements PatientHealthGoalService {

    @Autowired
    private PatientHealthGoalRepository patientHealthGoalRepository;

    @Autowired
    private ProgressDataRepository progressDataRepository;

    // Create a health goal
    @Override
    public PatientHealthGoalEntity createHealthGoal(PatientHealthGoalEntity healthGoal) {
        // Add any additional logic/validation as needed
        return patientHealthGoalRepository.save(healthGoal);
    }

    // Retrieve health goals by patientId
    @Override
    public List<PatientHealthGoalEntity> getHealthGoalsByPatientId(String patientId) {
        return patientHealthGoalRepository.findByPatientId(patientId);
    }

    // Update health goal progress
    @Override
    public PatientHealthGoalEntity updateHealthGoalProgress(Long goalId, String progressUpdate) {
        Optional<PatientHealthGoalEntity> optionalGoal = patientHealthGoalRepository.findById(goalId);
        optionalGoal.ifPresent(goal -> {
            // Update progress or perform any other relevant logic
            goal.setGoalStatus(progressUpdate);
            patientHealthGoalRepository.save(goal);
        });
        return optionalGoal.orElse(null);
    }

    // Delete a health goal
    @Override
    public void deleteHealthGoal(Long goalId) {
        patientHealthGoalRepository.deleteById(goalId);
    }

    // Get details of a health goal
    @Override
    public PatientHealthGoalEntity getHealthGoalDetails(Long goalId) {
        return patientHealthGoalRepository.findById(goalId).orElse(null);
    }

    // Get progress data for a health goal
    @Override
    public List<ProgressData> getProgressData(Long goalId) {
        return progressDataRepository.findByGoalId(goalId);
    }

    // Send notification
    @Override
    public void sendNotification(String patientId, String message) {
        // Implementation to send notification
    }

    // Update progress from an external source
    @Override
    public void updateProgressFromExternalSource(String patientId, ExternalDataDto data) {
        // Implementation to update progress from external source
        // Example: Save external progress data to ProgressData table
        ProgressData progressData = new ProgressData();
        progressData.setGoalId(data.getExternalGoalId());
        progressData.setUpdate(data.getExternalProgress());
        progressData.setTimestamp(data.getExternalUpdateTime());
        progressDataRepository.save(progressData);
    }
}

