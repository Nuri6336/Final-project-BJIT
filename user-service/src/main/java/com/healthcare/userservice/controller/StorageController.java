package com.healthcare.userservice.controller;

import com.healthcare.userservice.service.implementation.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class StorageController {

    @Autowired
    private StorageService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file) throws Exception {
        String uploadImage = service.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @PostMapping("/fileSystem/doctor")
    public ResponseEntity<?> uploadImageToFIleSystemOfDoctor(@RequestParam("image") MultipartFile file) throws Exception {
        String uploadImage = service.uploadImageToFileSystemDoctor(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/fileSystem/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String id) throws IOException {
        byte[] imageData=service.downloadImageFromFileSystem(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @DeleteMapping("/fileSystem/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable String id) throws Exception {
        service.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Image deleted successfully.");
    }

    @DeleteMapping("/fileSystem/doctor/delete/{id}")
    public ResponseEntity<?> deleteFileOfDoctor(@PathVariable String id) throws Exception {
        service.deleteImageOfDoctor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Image deleted successfully.");
    }
}
