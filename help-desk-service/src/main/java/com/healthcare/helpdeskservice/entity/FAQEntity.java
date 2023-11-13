package com.healthcare.helpdeskservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="faq")
public class FAQEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;

    private String question;
    private String answer;
    private String category;
}
