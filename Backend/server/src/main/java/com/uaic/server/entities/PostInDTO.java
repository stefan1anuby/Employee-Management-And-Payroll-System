package com.uaic.server.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostInDTO {

    private String text;
    private LocalDateTime timestamp;
    private boolean reaction;

    public PostInDTO() {
    }

    public PostInDTO(String text, LocalDateTime timestamp, boolean reaction) {
        this.text = text;
        this.timestamp = timestamp;
        this.reaction = reaction;
    }

}
