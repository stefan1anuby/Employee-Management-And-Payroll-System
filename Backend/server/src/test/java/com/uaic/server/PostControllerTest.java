package com.uaic.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uaic.server.controller.PostController;
import com.uaic.server.entities.Business;
import com.uaic.server.entities.Employee;
import com.uaic.server.entities.Post;
import com.uaic.server.entities.PostInDTO;
import com.uaic.server.entities.PostOutDTO;
import com.uaic.server.entities.UserOutDTO;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.PostService;
import com.uaic.server.services.UserService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    private Post testPost;
    private Employee testEmployee;
    private Employee testSecondEmployee;
    private Business testBusiness;
    private Business testSecondBusiness;
    private UserOutDTO testUser;
    private UserOutDTO testSecondUser;

    @BeforeEach
    public void setTestingVariables() {

        testBusiness = new Business();
        testBusiness.setName("Microsoft");
        testBusiness.setAddress("Iasi");
        testBusiness.setIndustry("IT");
        testBusiness.setId(UUID.randomUUID());

        testSecondBusiness = new Business();
        testSecondBusiness.setName("AMD");
        testSecondBusiness.setAddress("Iasi");
        testSecondBusiness.setIndustry("IT");
        testSecondBusiness.setId(UUID.randomUUID());

        testEmployee = new Employee();
        testEmployee.setName("Antoniu1001");
        testEmployee.setEmail("antoniu1001@gmail.com");
        testEmployee.setBusiness(testBusiness);

        testSecondEmployee = new Employee();
        testSecondEmployee.setName("George2002");
        testSecondEmployee.setEmail("george2002@gmail.com");
        testSecondEmployee.setBusiness(testSecondBusiness);

        testUser = new UserOutDTO();
        testUser.setName("Antoniu1001");
        testUser.setEmail("antoniu1001@gmail.com");

        testSecondUser = new UserOutDTO();
        testSecondUser.setName("George2002");
        testSecondUser.setEmail("george2002@gmail.com");

        testPost = new Post();
        testPost.setId(UUID.randomUUID());
        testPost.setAuthor(testUser.getEmail());
        testPost.setText("Best company in the world");
        testPost.setEmployee(testEmployee);

    }

    @Test
    public void testGetPost() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(postService.findPostById(testPost.getId())).thenReturn(Optional.of(testPost));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));
        boolean businessMatch = testEmployee.getBusiness().equals(testPost.getEmployee().getBusiness());

        assertTrue(businessMatch);

        ResponseEntity<PostOutDTO> response = postController.getPost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void testGetPostFail1() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(postService.findPostById(testPost.getId())).thenReturn(Optional.empty());

        ResponseEntity<PostOutDTO> response = postController.getPost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testGetPostFail2() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(postService.findPostById(testPost.getId())).thenReturn(Optional.of(testPost));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<PostOutDTO> response = postController.getPost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void testGetPostFail3() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testSecondUser);
        when(postService.findPostById(testPost.getId())).thenReturn(Optional.of(testPost));
        when(employeeService.findEmployeeByEmail(testSecondUser.getEmail()))
                .thenReturn(Optional.of(testSecondEmployee));

        boolean businessMatch = testSecondEmployee.getBusiness().equals(testPost.getEmployee().getBusiness());

        assertFalse(businessMatch);

        ResponseEntity<PostOutDTO> response = postController.getPost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void testCreatePost() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));

        PostInDTO postInDTO = new PostInDTO();
        postInDTO.setText("This is a good place");

        ResponseEntity<PostOutDTO> response = postController.createPost(postInDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getText()).isEqualTo("This is a good place");

    }

    @Test
    public void testCreatePostFail() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        PostInDTO postInDTO = new PostInDTO();
        postInDTO.setText("This is a good place");

        ResponseEntity<PostOutDTO> response = postController.createPost(postInDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void testUpdatePost() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        boolean emailMatch = testUser.getEmail().equals(testPost.getEmployee().getEmail());

        assertTrue(emailMatch);

        ResponseEntity<Void> response = postController.updatePost(testPost);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void testUpdatePostFail() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testSecondUser);
        boolean emailMatch = testSecondUser.getEmail().equals(testPost.getEmployee().getEmail());

        assertFalse(emailMatch);

        ResponseEntity<Void> response = postController.updatePost(testPost);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void testDeletePost() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(postService.checkPostById(testPost.getId())).thenReturn(true);
        when(postService.findPostById(testPost.getId())).thenReturn(Optional.of(testPost));
        boolean emailMatch = testUser.getEmail().equals(testPost.getEmployee().getEmail());

        assertTrue(emailMatch);

        ResponseEntity<Void> response = postController.deletePost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    public void testDeletePostFail1() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(postService.checkPostById(testPost.getId())).thenReturn(false);

        ResponseEntity<Void> response = postController.deletePost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testDeletePostFail2() {

        when(userService.getAuthenticatedUserInfo()).thenReturn(testSecondUser);
        when(postService.checkPostById(testPost.getId())).thenReturn(true);
        when(postService.findPostById(testPost.getId())).thenReturn(Optional.of(testPost));
        boolean emailMatch = testSecondUser.getEmail().equals(testPost.getEmployee().getEmail());

        assertFalse(emailMatch);

        ResponseEntity<Void> response = postController.deletePost(testPost.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

}
