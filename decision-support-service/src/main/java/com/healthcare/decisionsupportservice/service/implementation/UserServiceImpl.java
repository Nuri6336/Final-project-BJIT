package com.healthcare.decisionsupportservice.service.implementation;


import com.healthcare.decisionsupportservice.dto.UserDto;
import com.healthcare.decisionsupportservice.networkmanager.UserFeingClient;
import com.healthcare.decisionsupportservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserFeingClient userFiengClient;

    @Override
    public UserDto getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        return userFiengClient.userDetailsByEmail(userMail);
    }
}
