package com.healthcare.userservice.controller;

import com.healthcare.userservice.constants.AppConstants;
import com.healthcare.userservice.dto.AdminDto;
import com.healthcare.userservice.dto.UserLoginDto;
import com.healthcare.userservice.service.AdminService;
import com.healthcare.userservice.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/admin/register")
    public ResponseEntity<?> register (@RequestBody AdminDto adminDto) {
        try {
            String response = adminService.addUserAsAdmin(adminDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
            AdminDto adminDto = adminService.getUser(userLoginDto.getEmail());
            List<String> userRoles = new ArrayList<>();
            userRoles.add(adminDto.getRole());

            String accessToken = JWTUtils.generateToken(adminDto.getEmail(), userRoles);
            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("userId", adminDto.getRole());
            loginResponse.put("email", adminDto.getEmail());
            loginResponse.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + accessToken);

            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Wrong Credential!", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/admin/remove-user")
    public ResponseEntity<?> deleteAdminAccount(Long id){
        try {
            String response = adminService.removeUserAsAdmin(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/admin/remove-access")
    public ResponseEntity<?> removeAdminAccess(Long id){
        try {
            String response = adminService.removeAdminAccess(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/admin/permit-access")
    public ResponseEntity<?> permitAdminAccess(Long id){
        try {
            String response = adminService.permitAdminAccess(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
