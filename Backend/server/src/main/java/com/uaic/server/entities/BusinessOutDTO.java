package com.uaic.server.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class BusinessOutDTO {
    private UUID id;
    private String name;
    private String address;
    private String industry;

    public static BusinessOutDTO mapToBusinessOutDTO(Business business) {
        // Map Business to BusinessOutDTO
        BusinessOutDTO dto = new BusinessOutDTO();
        dto.setId(business.getId());
        dto.setName(business.getName());
        dto.setAddress(business.getAddress());
        dto.setIndustry(business.getIndustry());
        if (business.getEmployees() != null) {
            /*
            dto.setEmployees(business.getEmployees().stream()
                    .map(this::mapToEmployeeOutDTO)
                    .collect(Collectors.toList()));
             */
        }
        return dto;
    }
}
