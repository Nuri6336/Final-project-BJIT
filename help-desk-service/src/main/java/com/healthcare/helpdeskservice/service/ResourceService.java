package com.healthcare.helpdeskservice.service;

import com.healthcare.helpdeskservice.dto.ResourceDto;
import com.healthcare.helpdeskservice.exception.ValueNotFoundException;

import java.util.List;

public interface ResourceService {

    String addResource(ResourceDto resourceDto);
    String updateResource(Long resourceId, ResourceDto resourceDto) throws ValueNotFoundException;
    String deleteResource(Long resourceId) throws ValueNotFoundException;
    List<ResourceDto> viewAllResource();
    ResourceDto viewIndividualResource(Long resourceId) throws ValueNotFoundException;
}
