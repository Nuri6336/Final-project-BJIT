package com.healthcare.notificationservice.service;

import com.healthcare.notificationservice.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAllNotifications();
    NotificationDto getNotificationById(Long notificationId);
    List<NotificationDto> getNotificationsByRecipientId(String recipientId);
    List<NotificationDto> getUnreadNotifications(String recipientId);
    void sendNotification(NotificationDto notification);
    void markNotificationAsRead(Long notificationId);
    void deleteNotification(Long notificationId);
}

