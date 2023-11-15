package com.healthcare.decisionsupportservice.service;

import com.healthcare.decisionsupportservice.dto.UserDto;

public interface UserService {
    UserDto getCurrentUser() throws Exception;
}
