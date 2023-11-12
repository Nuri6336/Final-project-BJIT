package com.healthcare.helpdeskservice.service.implementation;

import com.healthcare.helpdeskservice.dto.ResourceAllocationDto;
import com.healthcare.helpdeskservice.dto.ResourceDto;
import com.healthcare.helpdeskservice.entity.ResourceAllocationEntity;
import com.healthcare.helpdeskservice.entity.ResourceEntity;
import com.healthcare.helpdeskservice.exception.ValueNotFoundException;
import com.healthcare.helpdeskservice.repository.ResourceAllocationRepository;
import com.healthcare.helpdeskservice.repository.ResourceRepository;
import com.healthcare.helpdeskservice.service.ResourceAllocationService;
import com.healthcare.helpdeskservice.service.ResourceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResourceServiceImplementation implements ResourceService, ResourceAllocationService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceAllocationRepository resourceAllocationRepository;

    @Override
    public String addResource(ResourceDto resourceDto){
        ResourceEntity resourceEntity = new ResourceEntity();

        resourceEntity.setResourceName(resourceDto.getResourceName());
        resourceEntity.setResourceType(resourceDto.getResourceType());
        resourceEntity.setResourceDescription(resourceDto.getResourceDescription());
        resourceEntity.setAvailability(resourceDto.getAvailability());

        resourceRepository.save(resourceEntity);

        return "Resource added successfully.";
    }

    @Override
    public String updateResource(Long resourceId, ResourceDto resourceDto) throws ValueNotFoundException {
        ResourceEntity resourceEntity = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ValueNotFoundException("No medicine found."));

        resourceEntity.setResourceName(resourceDto.getResourceName());
        resourceEntity.setResourceType(resourceDto.getResourceType());
        resourceEntity.setResourceDescription(resourceDto.getResourceDescription());
        resourceEntity.setAvailability(resourceDto.getAvailability());

        resourceRepository.save(resourceEntity);

        return "Updated successfully.";
    }

    @Override
    public String deleteResource(Long resourceId) throws ValueNotFoundException {
        ResourceEntity resourceEntity = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ValueNotFoundException("No Resource found."));

        resourceRepository.delete(resourceEntity);

        return "Resource has been deleted successfully.";
    }

    @Override
    public List<ResourceDto> viewAllResource(){
        List<ResourceEntity> resourceEntities = resourceRepository.findAll();

        List<ResourceDto> resourceDtos = new ArrayList<>();

        for (ResourceEntity resourceEntity: resourceEntities) {
            resourceDtos.add(new ModelMapper().map(resourceEntity, ResourceDto.class));
        }

        return resourceDtos;
    }

    @Override
    public ResourceDto viewIndividualResource(Long resourceId) throws ValueNotFoundException {
        ResourceEntity resourceEntity = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ValueNotFoundException("No medicine found."));

        return (new ModelMapper().map(resourceEntity, ResourceDto.class));
    }

    @Override
    public String allocateResource(String doctorId, ResourceAllocationDto resourceAllocationDto){
        if (isResourceAvailable(resourceAllocationDto)) {
            // Create a new ResourceAllocation and set its properties
            ResourceAllocationEntity resourceAllocation = new ResourceAllocationEntity();

            resourceAllocation.setResourceId(resourceAllocationDto.getResourceId());
            resourceAllocation.setPurpose(resourceAllocationDto.getPurpose());
            resourceAllocation.setStartTime(resourceAllocationDto.getStartTime());
            resourceAllocation.setEndTime(resourceAllocationDto.getEndTime());
            resourceAllocation.setDoctorId(resourceAllocationDto.getDoctorId());

            // Set the doctor for the resourceAllocation
            resourceAllocation.setDoctorId(doctorId);

            // Save the new resourceAllocation
            resourceAllocationRepository.save(resourceAllocation);

            return "Resource allocated successfully for doctor: " + doctorId;
        } else {
            return "Resource is not available at the specified time.";
        }
    }

    private boolean isResourceAvailable(ResourceAllocationDto resourceAllocationDto) {
        // Check for overlapping allocations based on the specified time range
        List<ResourceAllocationEntity> overlappingAllocations = resourceAllocationRepository.findOverlappingAllocations(
                resourceAllocationDto.getResourceId(),
                resourceAllocationDto.getStartTime(),
                resourceAllocationDto.getEndTime()
        );

        // If there are overlapping allocations, the resource is not available
        return overlappingAllocations.isEmpty();
    }

    @Override
    public String cancelAllocation(Long resourceAllocationId) throws ValueNotFoundException {
        ResourceAllocationEntity resourceAllocationEntity = resourceAllocationRepository.findById(resourceAllocationId)
                .orElseThrow(() -> new ValueNotFoundException("No value found."));

        resourceAllocationRepository.delete(resourceAllocationEntity);

        return "Resource allocation canceled.";
    }
}