package com.healthcare.helpdeskservice.service.implementation;

import com.healthcare.helpdeskservice.entity.FAQEntity;
import com.healthcare.helpdeskservice.exception.ValueNotFoundException;
import com.healthcare.helpdeskservice.repository.FAQRepository;
import com.healthcare.helpdeskservice.service.FAQService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FAQServiceImplementation implements FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Override
    public List<FAQEntity> getAllFAQs() {
        return faqRepository.findAll();
    }

    @Override
    public FAQEntity getFAQById(Long id) throws ValueNotFoundException {
        FAQEntity faqEntity = faqRepository.findById(id)
                .orElseThrow(() -> new ValueNotFoundException("No id found."));

        return faqEntity;
    }

    @Override
    public FAQEntity saveOrUpdateFAQ(FAQEntity faqEntity) {
        return faqRepository.save(faqEntity);
    }

    @Override
    public String deleteFAQ(Long id) throws ValueNotFoundException {
        FAQEntity faqEntity = faqRepository.findById(id)
                .orElseThrow(() -> new ValueNotFoundException("No id found."));

        faqRepository.delete(faqEntity);

        return "FAQ data deleted";
    }
}
