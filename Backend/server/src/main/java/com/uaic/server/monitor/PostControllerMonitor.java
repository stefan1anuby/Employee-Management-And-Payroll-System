package com.uaic.server.monitor;

import java.util.Optional;
import java.util.UUID;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.uaic.server.entities.Employee;
import com.uaic.server.entities.Post;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.PostService;
import com.uaic.server.services.UserService;

@Aspect
@Component
public class PostControllerMonitor {

    private static final Logger logger = LoggerFactory.getLogger(PostControllerMonitor.class);

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PostService postService;

    @Before("execution(* com.uaic.server.controller.PostController.createPost(..))")
    public void beforeCreatePost() {

        UserOutDTO user = userService.getAuthenticatedUserInfo();

        Optional<Employee> optionalEmployee = employeeService.findEmployeeByEmail(user.getEmail());
        if (!optionalEmployee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only employees can make posts");
        }

        logger.info("A post is created");
    }

    @After("execution(* com.uaic.server.controller.PostController.createPost(..))")
    public void afterCreatePost() {
        logger.info("A post was created");
    }

    @Before("execution(* com.uaic.server.controller.PostController.getPost(..))")
    public void beforeGetPost() {
        logger.info("A post is extracted");
    }

    @Before("execution(* com.uaic.server.controller.PostController.deletePost(..)) && args(postId)")
    public void beforeDeletePost(UUID postId) {

        UserOutDTO user = userService.getAuthenticatedUserInfo();

        Optional<Post> optionalPost = postService.findPostById(postId);
        if (!optionalPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        Post post = optionalPost.get();
        if (!post.getEmployee().getEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The connected user is not the one that made the post");
        }

        logger.info("A post is deleted");

    }

    @After("execution(* com.uaic.server.controller.PostController.deletePost(..))")
    public void afterDeletePost() {
        logger.info("A post was deleted");
    }

}
