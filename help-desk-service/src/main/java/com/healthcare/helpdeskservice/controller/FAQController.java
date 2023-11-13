package com.healthcare.helpdeskservice.controller;

import com.healthcare.helpdeskservice.entity.FAQEntity;
import com.healthcare.helpdeskservice.exception.ValueNotFoundException;
import com.healthcare.helpdeskservice.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help-desk")
public class FAQController {

    @Autowired
    private FAQService faqService;

    @GetMapping("/faq/all")
    public List<FAQEntity> getAllFAQs() {
        return faqService.getAllFAQs();
    }

    @GetMapping("/faq/individual/{id}")
    public ResponseEntity<FAQEntity> getFAQById(@PathVariable Long id) {
        try {
            FAQEntity faqEntity = faqService.getFAQById(id);
            return new ResponseEntity<>(faqEntity, HttpStatus.OK);
        } catch (ValueNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/faq/create")
    public ResponseEntity<FAQEntity> createOrUpdateFAQ(@RequestBody FAQEntity faqEntity) {
        FAQEntity savedFAQ = faqService.saveOrUpdateFAQ(faqEntity);
        return new ResponseEntity<>(savedFAQ, HttpStatus.CREATED);
    }

    @DeleteMapping("/faq/delete/{id}")
    public ResponseEntity<String> deleteFAQ(@PathVariable Long id) {
        try {
            String message = faqService.deleteFAQ(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ValueNotFoundException e) {
            return new ResponseEntity<>("FAQ not found", HttpStatus.NOT_FOUND);
        }
    }
}

