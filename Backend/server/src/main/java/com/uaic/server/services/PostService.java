package com.uaic.server.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uaic.server.entities.Post;
import com.uaic.server.repositories.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> findPostById(UUID id) {
        return postRepository.findById(id);
    }

    public boolean checkPostById(UUID id) {
        return postRepository.existsById(id);
    }

    public void deletePostById(UUID id) {
        postRepository.deleteById(id);
    }

}
