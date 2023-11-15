package com.healthcare.schedulingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private Long notificationId;
    private String recipientId;
    private String message;
    private String notificationType;
    private LocalDateTime timeStamp;
    private LocalDateTime sendTime;
    private String status;
}
