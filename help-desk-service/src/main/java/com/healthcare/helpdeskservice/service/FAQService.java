package com.healthcare.helpdeskservice.service;

import com.healthcare.helpdeskservice.entity.FAQEntity;
import com.healthcare.helpdeskservice.exception.ValueNotFoundException;

import java.util.List;

public interface FAQService {

    List<FAQEntity> getAllFAQs();
    FAQEntity getFAQById(Long id) throws ValueNotFoundException;
    FAQEntity saveOrUpdateFAQ(FAQEntity faqEntity);
    String deleteFAQ(Long id) throws ValueNotFoundException;
}
