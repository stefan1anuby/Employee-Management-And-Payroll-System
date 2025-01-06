package com.uaic.server.entities;

import lombok.Data;

@Data
public class BusinessInDTO {

    private String name;
    private String address;
    private String industry;

    // Default constructor
    public BusinessInDTO() {
    }

    // Parameterized constructor
    public BusinessInDTO(String name, String address, String industry) {
        this.name = name;
        this.address = address;
        this.industry = industry;
    }
}
