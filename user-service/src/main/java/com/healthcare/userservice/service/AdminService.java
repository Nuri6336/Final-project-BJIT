package com.healthcare.userservice.service;

import com.healthcare.userservice.dto.AdminDto;
import com.healthcare.userservice.exception.UserAlreadyExistsException;
import com.healthcare.userservice.exception.UserNotExistsException;

public interface AdminService {

    String permitAdminAccess(Long id) throws UserNotExistsException;
    String removeAdminAccess(Long id) throws UserNotExistsException;
    AdminDto getUser(String email) throws UserNotExistsException;

    String addUserAsAdmin(AdminDto adminDto) throws UserAlreadyExistsException;
    String removeUserAsAdmin(Long id) throws UserNotExistsException;
}
