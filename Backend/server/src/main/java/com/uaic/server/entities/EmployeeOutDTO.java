package com.uaic.server.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeOutDTO {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String department;
    private Employee.Role role;

    private UUID businessId;
    private String businessName;

    public static EmployeeOutDTO mapToEmployeeOutDTO(Employee employee) {
        EmployeeOutDTO dto = new EmployeeOutDTO();
        dto.setId(employee.getId());
        dto.setBusinessId(employee.getBusiness().getId());
        dto.setBusinessName(employee.getBusiness().getName());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setRole(employee.getRole());
        dto.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getName() : null); // Handle null department
        return dto;
    }
}

