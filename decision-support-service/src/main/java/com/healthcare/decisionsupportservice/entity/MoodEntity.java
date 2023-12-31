package com.healthcare.decisionsupportservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mood")
public class MoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moodId;
    private Long userId;
    private String moodState;
    private String stressType;
    private LocalDateTime timeStamp;
    private String moodNote;
}
