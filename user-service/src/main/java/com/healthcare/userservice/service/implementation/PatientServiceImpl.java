package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.PatientEditDto;
import com.healthcare.userservice.dto.PatientInfoDto;
import com.healthcare.userservice.dto.PatientRegisterDto;
import com.healthcare.userservice.entity.PatientEntity;
import com.healthcare.userservice.exception.UserAlreadyExistsException;
import com.healthcare.userservice.exception.UserNotExistsException;
import com.healthcare.userservice.repository.PatientRepository;
import com.healthcare.userservice.service.PatientService;
import com.healthcare.userservice.utils.JWTUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
public class PatientServiceImpl implements PatientService, UserDetailsService {

    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;

    public PatientServiceImpl(PatientRepository patientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String registerPatient(PatientRegisterDto patientRegisterDto) throws UserAlreadyExistsException {
        if(patientRepository.findByEmail(patientRegisterDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Email already exists!");

        String uniqueId = ("PAT" + JWTUtils.generateRandomString(5)).toUpperCase();

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFname(patientRegisterDto.getFname());
        patientEntity.setLname(patientRegisterDto.getLname());
        patientEntity.setEmail(patientRegisterDto.getEmail());
        patientEntity.setGender(patientRegisterDto.getGender());
        patientEntity.setMobile(patientRegisterDto.getMobile());
        patientEntity.setPatientId(uniqueId);
        patientEntity.setRole("PATIENT");
        patientEntity.setDateOfRegistration(LocalDateTime.now());

        String recipientEmail = patientRegisterDto.getEmail();

        String randomPassword = JWTUtils.generateRandomString(12);

        if (patientRegisterDto.getPassword() != null){
            patientEntity.setPassword(bCryptPasswordEncoder.encode(patientRegisterDto.getPassword()));
        }else {
            String emailSubject = "Patient login password";
            String emailText = "Welcome to Healthcare ,\n" +
                    "\n" +
                    "Your registration process with " + recipientEmail + " is successfully completed, and your password is "+ randomPassword +"\n" +
                    "\n" +
                    "Thank you.";
            emailService.sendEmail(recipientEmail, emailSubject, emailText);
            patientEntity.setPassword(bCryptPasswordEncoder.encode(randomPassword));
        }

        patientRepository.save(patientEntity);

        return "Patients successfully registered.";
    }

    @Override
    public PatientInfoDto getUser(String email) throws UserNotExistsException {
        PatientEntity patientEntity = patientRepository
                .findByEmail(email)
                .orElseThrow(()->new UserNotExistsException("User not found!"));

        return new ModelMapper().map(patientEntity, PatientInfoDto.class);
    }

    @Override
    public PatientInfoDto getPatientInfoById(Long id) throws UserNotExistsException {
        PatientEntity patientEntity = patientRepository
                .findById(id)
                .orElseThrow(()->new UserNotExistsException("User not found!"));

        return new ModelMapper().map(patientEntity, PatientInfoDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PatientEntity patientEntity = patientRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        return new User(patientEntity.getEmail(),patientEntity.getPassword(),
                true,true,true,true,new ArrayList<>());
    }

    @Override
    public PatientInfoDto getUserProfile() throws UserNotExistsException {
        PatientEntity patientEntity = getCurrentUser();
        return new ModelMapper().map(patientEntity, PatientInfoDto.class);
    }

    private PatientEntity getCurrentUser() throws UserNotExistsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return patientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotExistsException(AppConstants.TOKEN_INVALID));
    }

    @Override
    public String editPatientProfile(PatientEditDto patientEditDto) throws UserNotExistsException {
        PatientEntity patientEntity = patientRepository.findByEmail(getCurrentUser().getEmail())
                .orElseThrow(()->new UserNotExistsException("User not found!"));

        patientEntity.setFname(patientEditDto.getFname());
        patientEntity.setLname(patientEditDto.getLname());
        patientEntity.setAddress(patientEditDto.getAddress());
        patientEntity.setDateOfBirth(patientEditDto.getDateOfBirth());
        patientEntity.setPatient_image(patientEditDto.getPatient_image());
        patientEntity.setMobile(patientEditDto.getMobile());
        patientEntity.setGender(patientEditDto.getGender());

        patientRepository.save(patientEntity);

        return "Profile updated successfully.";
    }

    public String deletePatientAccount() throws UserNotExistsException{
        PatientEntity patientEntity = getCurrentUser();
        patientRepository.delete(patientEntity);

        return "Account has been deleted successfully!";
    }
}
