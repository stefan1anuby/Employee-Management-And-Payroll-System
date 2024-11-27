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
}

