package com.uaic.server.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostInDTO {

    private String text;
    private String author;
    private LocalDateTime timestamp;

    public PostInDTO() {
    }

    public PostInDTO(String text, String author, LocalDateTime timestamp) {
        this.text = text;
        this.author = author;
        this.timestamp = timestamp;
    }

}
