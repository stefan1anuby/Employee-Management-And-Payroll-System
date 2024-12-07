package com.uaic.server.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uaic.server.entities.Employee;
import com.uaic.server.entities.Post;
import com.uaic.server.entities.PostInDTO;
import com.uaic.server.entities.PostOutDTO;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.PostService;
import com.uaic.server.services.UserService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final EmployeeService employeeService;
    private final UserService userService;

    public PostController(PostService postService, EmployeeService employeeService, UserService userService) {
        this.postService = postService;
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostOutDTO> getPost(@PathVariable UUID postId) {

        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        // Verify if the post exists
        Optional<Post> optionalPost = postService.findPostById(postId);
        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verify if the connected user is an employee at the same business
        // as the person that made the post
        Post post = optionalPost.get();
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByEmail(userInfo.getEmail());
        if (!optionalEmployee.isPresent() ||
                (optionalEmployee.isPresent()
                        && !optionalEmployee.get().getBusiness().equals(post.getEmployee().getBusiness()))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        PostOutDTO postOutDTO = PostOutDTO.mapToPostOutDTO(post);
        return new ResponseEntity<>(postOutDTO, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<PostOutDTO> createPost(@RequestBody PostInDTO postInDTO) {

        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        // Verify if the user is an employee
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByEmail(userInfo.getEmail());
        if (!optionalEmployee.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Convert from Optional<Employee> to Employee
        Employee employee = optionalEmployee.get();

        // Create a new Post class
        Post post = new Post(
                postInDTO.getText(),
                postInDTO.getAuthor(),
                postInDTO.getTimestamp());
        post.setEmployee(employee);

        // Save the created post into the database
        postService.createPost(post);

        PostOutDTO postOutDTO = PostOutDTO.mapToPostOutDTO(post);
        return new ResponseEntity<>(postOutDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable UUID postId, @RequestBody Post post) {

        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        if (!postService.checkPostById(postId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if the connected user is the one that made the post
        if (!userInfo.getEmail().equals(post.getEmployee().getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Update the id of the post and save it to the database
        post.setId(postId);
        postService.createPost(post);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {

        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        if (!postService.checkPostById(postId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Post post = postService.findPostById(postId).get();
        if (!userInfo.getEmail().equals(post.getEmployee().getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Delete the post
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
