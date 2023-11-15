package com.healthcare.schedulingservice.networkmanager;

import com.healthcare.schedulingservice.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", configuration = FeignClientConfiguration.class)
public interface NotificationFeingClient {

    @PostMapping("/notification/add")
    void sendNotification(@RequestBody NotificationDto notificationDto);
}
