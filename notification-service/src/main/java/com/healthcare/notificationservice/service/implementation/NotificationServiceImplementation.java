package com.healthcare.notificationservice.service.implementation;

import com.healthcare.notificationservice.dto.NotificationDto;
import com.healthcare.notificationservice.entity.NotificationEntity;
import com.healthcare.notificationservice.repository.NotificationRepository;
import com.healthcare.notificationservice.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImplementation implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationDto> getAllNotifications() {
        List<NotificationEntity> notificationEntities = notificationRepository.findAll();
        return mapEntitiesToDtos(notificationEntities);
    }

    @Override
    public NotificationDto getNotificationById(Long notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationId).orElse(null);
        return mapEntityToDto(notificationEntity);
    }

    @Override
    public List<NotificationDto> getNotificationsByRecipientId(String recipientId) {
        List<NotificationEntity> notificationEntities = notificationRepository.findByRecipientId(recipientId);
        return mapEntitiesToDtos(notificationEntities);
    }

    @Override
    public List<NotificationDto> getUnreadNotifications(String recipientId) {
        List<NotificationEntity> unreadEntities = notificationRepository.findUnreadNotifications(recipientId);
        return mapEntitiesToDtos(unreadEntities);
    }

    @Override
    public void sendNotification(NotificationDto notificationDto) {
        NotificationEntity notificationEntity = mapDtoToEntity(notificationDto);
        notificationEntity.setTimeStamp(LocalDateTime.now());
        notificationRepository.save(notificationEntity);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationId).orElse(null);
        if (notificationEntity != null) {
            notificationEntity.setStatus("READ");
            notificationRepository.save(notificationEntity);
        }
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    // Helper methods for mapping between DTO and Entity
    private NotificationDto mapEntityToDto(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        NotificationDto dto = new NotificationDto();
        dto.setNotificationId(entity.getNotificationId());
        dto.setRecipientId(entity.getRecipientId());
        dto.setMessage(entity.getMessage());
        dto.setNotificationType(entity.getNotificationType());
        dto.setTimeStamp(entity.getTimeStamp());
        dto.setSendTime(entity.getSendTime());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    private List<NotificationDto> mapEntitiesToDtos(List<NotificationEntity> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private NotificationEntity mapDtoToEntity(NotificationDto dto) {
        if (dto == null) {
            return null;
        }

        NotificationEntity entity = new NotificationEntity();
        entity.setRecipientId(dto.getRecipientId());
        entity.setMessage(dto.getMessage());
        entity.setNotificationType(dto.getNotificationType());
        entity.setSendTime(dto.getSendTime());
        entity.setStatus(dto.getStatus());

        return entity;
    }
}

