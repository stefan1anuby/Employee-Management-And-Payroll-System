package com.uaic.server.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class PostOutDTO {

    private UUID id;
    private String text;
    private String author;
    private LocalDateTime timestamp;
    private boolean reaction;

    public static PostOutDTO mapToPostOutDTO(Post post) {
        PostOutDTO postOutDTO = new PostOutDTO();
        postOutDTO.setId(post.getId());
        postOutDTO.setText(post.getText());
        postOutDTO.setAuthor(post.getAuthor());
        postOutDTO.setTimestamp(post.getTimestamp());
        postOutDTO.setReaction(post.getReaction());
        return postOutDTO;
    }

}
