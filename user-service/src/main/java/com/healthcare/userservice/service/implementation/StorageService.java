package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.entity.FileData;
import com.healthcare.userservice.entity.PatientEntity;
import com.healthcare.userservice.entity.UserEntity;
import com.healthcare.userservice.exception.UserNotExistsException;
import com.healthcare.userservice.repository.FileDataRepository;
import com.healthcare.userservice.repository.PatientRepository;
import com.healthcare.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    private final String FOLDER_PATH = "D:/J2EE/HealthcareStorage/";


    public String uploadImageToFileSystem(MultipartFile file) throws Exception {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        PatientEntity patientEntity = patientRepository.findByPatientUniqueId(getCurrentUser().getUniqueId())
                .orElseThrow(() -> new UserNotExistsException(AppConstants.USER_NOT_FOUND));

        FileData fileData = fileDataRepository.save(FileData.builder()
                .uniqueId(getCurrentUser().getUniqueId())
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        patientEntity.setPatientImage(filePath);
        patientRepository.save(patientEntity);

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String uniqueId) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByUniqueId(uniqueId);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    public void deleteImage(String uniqueId) throws Exception {
        Optional<FileData> fileDataOptional = fileDataRepository.findByUniqueId(uniqueId);
        PatientEntity patientEntity = patientRepository.findByPatientUniqueId(getCurrentUser().getUniqueId())
                .orElseThrow(() -> new UserNotExistsException(AppConstants.USER_NOT_FOUND));

        if (fileDataOptional.isPresent()) {
            FileData fileData = fileDataOptional.get();
            String filePath = fileData.getFilePath();

            // Delete the file from storage
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);

            // Delete the file data from the database
            fileDataRepository.delete(fileData);
            patientEntity.setPatientImage(null);
            patientRepository.save(patientEntity);
        } else {
            // Handle the case where the file with the given uniqueId is not found
            throw new FileNotFoundException("File not found for uniqueId: " + uniqueId);
        }
    }


    private UserEntity getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new Exception(AppConstants.TOKEN_INVALID));
    }
}
