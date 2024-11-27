package com.uaic.server.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class BusinessOutDTO {
    private UUID id;
    private String name;
    private String address;
    private String industry;
}
