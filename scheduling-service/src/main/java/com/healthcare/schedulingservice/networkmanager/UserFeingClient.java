package com.healthcare.schedulingservice.networkmanager;

import com.healthcare.schedulingservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignClientConfiguration.class)
public interface UserFeingClient {

    @GetMapping("/users/{email}")
    UserDto userDetailsByEmail(@PathVariable String email);
}
