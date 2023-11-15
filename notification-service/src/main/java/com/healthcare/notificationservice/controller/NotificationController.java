package com.healthcare.notificationservice.controller;

import com.healthcare.notificationservice.dto.NotificationDto;
import com.healthcare.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long notificationId) {
        NotificationDto notification = notificationService.getNotificationById(notificationId);
        return notification != null ?
                new ResponseEntity<>(notification, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByRecipientId(@PathVariable String recipientId) {
        List<NotificationDto> notifications = notificationService.getNotificationsByRecipientId(recipientId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/unread/{recipientId}")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@PathVariable String recipientId) {
        List<NotificationDto> unreadNotifications = notificationService.getUnreadNotifications(recipientId);
        return new ResponseEntity<>(unreadNotifications, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationDto notificationDto) {
        notificationService.sendNotification(notificationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

