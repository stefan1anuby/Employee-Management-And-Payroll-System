package com.uaic.server.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostInDTO {

    private String text;
    private LocalDateTime timestamp;

    public PostInDTO() {
    }

    public PostInDTO(String text, LocalDateTime timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

}
