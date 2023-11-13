package com.healthcare.helpdeskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQDto {

    private Long faqId;
    private String question;
    private String answer;
    private String category;
}
