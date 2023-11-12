package com.healthcare.helpdeskservice.controller;

import com.healthcare.helpdeskservice.dto.ResourceAllocationDto;
import com.healthcare.helpdeskservice.dto.ResourceDto;
import com.healthcare.helpdeskservice.service.ResourceAllocationService;
import com.healthcare.helpdeskservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help-desk")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceAllocationService resourceAllocationService;

    @PostMapping("/resource/add")
    public ResponseEntity<?> addResource (@RequestBody ResourceDto resourceDto) {
        try {
            String response = resourceService.addResource(resourceDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/resource/update/{resourceId}")
    public ResponseEntity<?> updateResource (@PathVariable Long resourceId,@RequestBody ResourceDto resourceDto) {
        try {
            String response = resourceService.updateResource(resourceId, resourceDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/resource/delete/{resourceId}")
    public ResponseEntity<?> deleteResource (@PathVariable Long resourceId) {
        try {
            String response = resourceService.deleteResource(resourceId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/resource/view-individual-resource/{resourceId}")
    public ResponseEntity<?> viewIndividualResource (@PathVariable Long resourceId) {
        try {
            ResourceDto resourceDto = resourceService.viewIndividualResource(resourceId);
            return new ResponseEntity<>(resourceDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/resource/view-all-resource")
    public ResponseEntity<?> viewAllResource () {
        try {
            List<ResourceDto> resourceDtos = resourceService.viewAllResource();
            return new ResponseEntity<>(resourceDtos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resource/allocate/{doctorId}")
    public ResponseEntity<?> allocateResource (@PathVariable String doctorId,
                                               @RequestBody ResourceAllocationDto resourceAllocationDto) {
        try {
            String response = resourceAllocationService.allocateResource(doctorId, resourceAllocationDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/resource/cancel-allocation/{resourceAllocationId}")
    public ResponseEntity<?> allocateResource (@PathVariable Long resourceAllocationId) {
        try {
            String response = resourceAllocationService.cancelAllocation(resourceAllocationId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
