package com.healthcare.notificationservice.repository;

import com.healthcare.notificationservice.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByRecipientId(String recipientId);
    List<NotificationEntity> findByRecipientIdAndStatus(String recipientId, String status);
}
