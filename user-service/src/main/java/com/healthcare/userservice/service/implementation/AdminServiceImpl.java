package com.healthcare.userservice.service.implementation;

import com.healthcare.userservice.dto.AdminDto;
import com.healthcare.userservice.entity.AdminEntity;
import com.healthcare.userservice.exception.UserAlreadyExistsException;
import com.healthcare.userservice.exception.UserNotExistsException;
import com.healthcare.userservice.repository.AdminRepository;
import com.healthcare.userservice.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String addUserAsAdmin(AdminDto adminDto) throws UserAlreadyExistsException {
        if(adminRepository.findByEmail(adminDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Email already exists!");

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setEmail(adminDto.getEmail());
        adminEntity.setPassword(bCryptPasswordEncoder.encode(adminDto.getPassword()));
        adminEntity.setRole("ADMIN");

        adminRepository.save(adminEntity);

        return "User can now act as Admin.";
    }

    @Override
    public String removeUserAsAdmin(Long id) throws UserNotExistsException {
        AdminEntity adminEntity = adminRepository.findById(id)
                        .orElseThrow(() -> new UserNotExistsException("User not exist"));

        adminRepository.delete(adminEntity);

        return "User removed from database.";
    }

    @Override
    public String permitAdminAccess(Long id) throws UserNotExistsException{
        AdminEntity adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException("User not exist"));

        adminEntity.setRole("ADMIN");

        return "User now permitted to access as Admin.";
    }

    @Override
    public String removeAdminAccess(Long id) throws UserNotExistsException{
        AdminEntity adminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException("User not exist"));

        adminEntity.setRole("UNKNOWN");

        return "Permit removed for this user.";
    }

    @Override
    public AdminDto getUser(String email) throws UserNotExistsException{
        AdminEntity adminEntity = adminRepository
                .findByEmail(email)
                .orElseThrow(()->new UserNotExistsException("User not found!"));

        return new ModelMapper().map(adminEntity, AdminDto.class);
    }
}
