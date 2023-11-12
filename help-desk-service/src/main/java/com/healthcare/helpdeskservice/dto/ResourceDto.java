package com.healthcare.helpdeskservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceDto {

    private Long resourceId;
    private String resourceType;
    private String resourceName;
    private String availability;
    private String resourceDescription;
}
