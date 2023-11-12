package com.healthcare.helpdeskservice.service;

import com.healthcare.helpdeskservice.dto.ResourceAllocationDto;
import com.healthcare.helpdeskservice.exception.ValueNotFoundException;

public interface ResourceAllocationService {

    String allocateResource(String doctorId, ResourceAllocationDto resourceAllocationDto);
    String cancelAllocation(Long resourceAllocationId) throws ValueNotFoundException;

}
