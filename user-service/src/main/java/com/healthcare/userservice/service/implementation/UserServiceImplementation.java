package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.UserDto;
import com.healthcare.userservice.entity.UserEntity;
import com.healthcare.userservice.repository.UserRepository;
import com.healthcare.userservice.service.UserService;
import com.healthcare.userservice.utils.JWTUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public EmailService emailService;
    public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new Exception("Email already exists!");
        UserEntity userEntity = new UserEntity();

        userEntity.setFname(user.getFname());
        userEntity.setLname(user.getLname());
        userEntity.setEmail(user.getEmail());
        userEntity.setGender(user.getGender());
        userEntity.setMobile(user.getMobile());

        if (user.getRole() == AppConstants.ROLE_PATIENT){
            String uniqueId = ("PAT" + JWTUtils.generateString(5)).toUpperCase();
            userEntity.setUniqueId(uniqueId);
        } else if (user.getRole() == AppConstants.ROLE_DOCTOR) {
            String uniqueId = ("DOC" + JWTUtils.generateString(5)).toUpperCase();
            userEntity.setUniqueId(uniqueId);
        }else {
            String uniqueId = ("ADM" + JWTUtils.generateString(5)).toUpperCase();
            userEntity.setUniqueId(uniqueId);
        }

        userEntity.setRole(user.getRole());
        userEntity.setDateOfRegistration(LocalDateTime.now());

        String recipientEmail = user.getEmail();
        String randomPassword = JWTUtils.generateString(12);

        if (user.getPassword() != null){
            userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }else {
            String emailSubject = "Patient login password";
            String emailText = "Welcome to Healthcare ,\n" +
                    "\n" +
                    "Your registration process with " + recipientEmail + " is successfully completed, and your password is "+ randomPassword +"\n" +
                    "\n" +
                    "Thank you.";
            emailService.sendEmail(recipientEmail, emailSubject, emailText);
            userEntity.setPassword(bCryptPasswordEncoder.encode(randomPassword));
        }

        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnedValue = new ModelMapper().map(storedUserDetails,UserDto.class);

        List<String> userRoles = new ArrayList<>();
        userRoles.add(user.getRole());

        String accessToken = JWTUtils.generateToken(userEntity.getEmail(), userRoles);
        returnedValue.setAccessToken(AppConstants.TOKEN_PREFIX + accessToken);
        return returnedValue;
    }
    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity = userRepository
                .findByEmail(email)
                .orElseThrow(()->new Exception("User not found!"));
        return new ModelMapper().map(userEntity, UserDto.class);
    }
    @Override
    public UserDto getUserByUserId(Long userId) throws Exception {
        UserEntity userEntity = userRepository
                .findByUserId(userId)
                .orElseThrow(()->new Exception("User not found!"));
        return new ModelMapper().map(userEntity, UserDto.class);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
        return new User(userEntity.getEmail(),userEntity.getPassword(),
                true,true,true,true,new ArrayList<>());
    }


    public UserDto getUserProfile() throws Exception {
        UserEntity userEntity = getCurrentUser();
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    private UserEntity getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new Exception(AppConstants.TOKEN_INVALID));
    }

    public void editUserProfile(UserDto userDto) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(getCurrentUser().getUserId())
                .orElseThrow(()->new Exception("User not found!"));

        userEntity.setGender(userDto.getGender() != null ? userDto.getGender() : userEntity.getGender());
        userEntity.setRole(userDto.getRole() != null ? userDto.getRole() : userEntity.getRole());

        userRepository.save(userEntity);
    }
}
