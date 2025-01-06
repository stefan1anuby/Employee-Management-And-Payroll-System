package com.uaic.server.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uaic.server.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    public Optional<Post> findByText(String text);

    public Optional<Post> findByAuthor(String author);

    public Optional<Post> findByTimestamp(LocalDateTime timestamp);

    public boolean existsByAuthor(String author);

    public void deleteByAuthor(String author);

}
