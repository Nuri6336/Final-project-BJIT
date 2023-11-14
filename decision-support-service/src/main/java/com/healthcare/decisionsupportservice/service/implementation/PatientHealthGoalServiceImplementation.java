package com.healthcare.decisionsupportservice.service.implementation;

import com.healthcare.decisionsupportservice.dto.ExternalDataDto;
import com.healthcare.decisionsupportservice.dto.PatientHealthGoalDto;
import com.healthcare.decisionsupportservice.dto.UserDto;
import com.healthcare.decisionsupportservice.entity.PatientHealthGoalEntity;
import com.healthcare.decisionsupportservice.entity.ProgressData;
import com.healthcare.decisionsupportservice.exception.ValueNotFoundException;
import com.healthcare.decisionsupportservice.networkmanager.UserFeingClient;
import com.healthcare.decisionsupportservice.repository.PatientHealthGoalRepository;
import com.healthcare.decisionsupportservice.repository.ProgressDataRepository;
import com.healthcare.decisionsupportservice.service.PatientHealthGoalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientHealthGoalServiceImplementation implements PatientHealthGoalService {

    @Autowired
    private PatientHealthGoalRepository patientHealthGoalRepository;

    @Autowired
    private ProgressDataRepository progressDataRepository;

    @Autowired
    private UserFeingClient userFeingClient;

    private UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        return userFeingClient.userDetailsByEmail(userMail);
    }

    private String getCurrentUserId() {
        return getCurrentUser().getUniqueId();
    }

    // Create a health goal
    @Override
    public PatientHealthGoalEntity createHealthGoal(PatientHealthGoalDto patientHealthGoalDto) {
        PatientHealthGoalEntity patientHealthGoal = new PatientHealthGoalEntity();

        patientHealthGoal.setPatientId(getCurrentUserId());
        patientHealthGoal.setGoalDescription(patientHealthGoalDto.getGoalDescription());
        patientHealthGoal.setTargetValue(patientHealthGoalDto.getTargetValue());
        patientHealthGoal.setGoalName(patientHealthGoalDto.getGoalName());
        patientHealthGoal.setGoalStatus("In progress");
        patientHealthGoal.setStartDate(LocalDateTime.now());
        patientHealthGoal.setUpdateMessage("You just started your goal.");

        return patientHealthGoalRepository.save(patientHealthGoal);
    }

    // Retrieve health goals by patientId
    @Override
    public List<PatientHealthGoalEntity> getHealthGoalsByPatientId(String patientId) {
        return patientHealthGoalRepository.findByPatientId(patientId);
    }

    // Update health goal progress
    @Override
    public PatientHealthGoalEntity updateHealthGoalProgress(Long goalId, double progressValue) throws ValueNotFoundException {
        PatientHealthGoalEntity goal = patientHealthGoalRepository.findById(goalId)
                .orElseThrow(() -> new ValueNotFoundException("Value not found."));

        double value1 = goal.getGoalValue() + progressValue;

        if (value1 > goal.getTargetValue()){
            goal.setGoalStatus("Completed");
            goal.setEndDate(LocalDateTime.now());
            goal.setUpdateMessage("You have reached your goal");
        }
        else {
            double value2 = (value1 / goal.getTargetValue()) * 100;
            goal.setUpdateMessage("You have completed " + value2 + "% of your goal.");
        }

        goal.setGoalValue(value1);
        patientHealthGoalRepository.save(goal);

        return goal;
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
        progressData.setUpdateValue(data.getExternalProgress());
        progressData.setTimestamp(data.getExternalUpdateTime());
        progressDataRepository.save(progressData);
    }
}

