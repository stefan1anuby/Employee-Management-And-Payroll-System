package com.uaic.server;

import com.uaic.server.controller.BusinessController;
import com.uaic.server.entities.*;
import com.uaic.server.services.BusinessService;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessControllerTest {

    @Mock
    private BusinessService businessService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BusinessController businessController;

    private Business testBusiness;
    private Employee testEmployee;
    private UserOutDTO testUser;
    private Post testPost;

    @BeforeEach
    public void setUp() {
        testBusiness = new Business("Test Business", "123 Test St", "IT");
        testBusiness.setId(UUID.randomUUID());

        testEmployee = new Employee();
        testEmployee.setName("John Doe");
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setRole(Employee.Role.ADMIN);
        testEmployee.setBusiness(testBusiness);

        testUser = new UserOutDTO();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");


        testPost = new Post();
        testPost.setId(UUID.randomUUID());
        testPost.setAuthor(testUser.getEmail());
        testPost.setText("This is a test post.");

        testEmployee.setPosts(Collections.singletonList(testPost));
    }

    @Test
    public void testGetBusinesses() {
        List<Business> businesses = Collections.singletonList(testBusiness);
        when(businessService.findBusinesses()).thenReturn(businesses);

        ResponseEntity<Iterable<BusinessOutDTO>> response = businessController.getBusinesses();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void testGetBusiness() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));

        ResponseEntity<Business> response = businessController.getBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testBusiness);
    }

    @Test
    public void testFailGetBusiness() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.empty());

        ResponseEntity<Business> response = businessController.getBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testCreateBusiness() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(businessService.createBusiness(any(Business.class))).thenReturn(testBusiness);
        when(employeeService.createOrUpdateEmployee(any(Employee.class))).thenReturn(testEmployee);

        BusinessInDTO businessInDTO = new BusinessInDTO();
        businessInDTO.setName("Test Business");
        businessInDTO.setAddress("123 Test St");
        businessInDTO.setIndustry("IT");

        ResponseEntity<BusinessOutDTO> response = businessController.createBusiness(businessInDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getName()).isEqualTo("Test Business");
    }

    @Test
    public void testFailCreateBusiness() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));

        BusinessInDTO businessInDTO = new BusinessInDTO();
        businessInDTO.setName("Test Business");
        businessInDTO.setAddress("123 Test St");
        businessInDTO.setIndustry("IT");

        ResponseEntity<BusinessOutDTO> response = businessController.createBusiness(businessInDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }

    @Test
    public void testUpdateBusiness() {
        when(businessService.checkExistentBusinessById(testBusiness.getId())).thenReturn(true);

        ResponseEntity<Void> response = businessController.updateBusiness(testBusiness.getId(), testBusiness);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testFailUpdateBusiness() {

        when(businessService.checkExistentBusinessById(testBusiness.getId())).thenReturn(false);
        ResponseEntity<Void> response = businessController.updateBusiness(testBusiness.getId(), testBusiness);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteBusiness() {
        when(businessService.checkExistentBusinessById(testBusiness.getId())).thenReturn(true);

        ResponseEntity<Void> response = businessController.deleteBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testFailDeleteBusiness() {
        when(businessService.checkExistentBusinessById(testBusiness.getId())).thenReturn(false);

        ResponseEntity<Void> response = businessController.deleteBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetEmployeesByBusiness() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(businessService.getEmployeesByBusiness(testBusiness.getId())).thenReturn(Collections.singletonList(testEmployee));

        ResponseEntity<Iterable<EmployeeOutDTO>> response = businessController.getEmployeesByBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void testFailGetEmployeesByBusiness() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.empty());

        ResponseEntity<Iterable<EmployeeOutDTO>> response = businessController.getEmployeesByBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddEmployeeToBusiness() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(employeeService.createOrUpdateEmployee(any(Employee.class))).thenReturn(testEmployee);

        ResponseEntity<EmployeeOutDTO> response = businessController.addEmployeeToBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testFailAddEmployeeToBusiness1() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.empty());


        ResponseEntity<EmployeeOutDTO> response = businessController.addEmployeeToBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testFailAddEmployeeToBusiness2() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));


        ResponseEntity<EmployeeOutDTO> response = businessController.addEmployeeToBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }

    // Add test for updateEmployeeInBusiness
    @Test
    public void testUpdateEmployeeInBusiness() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(true);

        ResponseEntity<Void> response = businessController.updateEmployeeInBusiness(testBusiness.getId(), testEmployee.getId(), testEmployee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testFailUpdateEmployeeInBusiness1() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = businessController.updateEmployeeInBusiness(testBusiness.getId(), testEmployee.getId(), testEmployee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testFailUpdateEmployeeInBusiness2() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(false);

        ResponseEntity<Void> response = businessController.updateEmployeeInBusiness(testBusiness.getId(), testEmployee.getId(), testEmployee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void testDeleteEmployeeFromBusiness() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(true);

        ResponseEntity<Void> response = businessController.deleteEmployeeFromBusiness(testBusiness.getId(), testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(employeeService, times(1)).deleteEmployeeById(testEmployee.getId());
    }

    @Test
    public void testFailDeleteEmployeeFromBusiness1() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.empty());


        ResponseEntity<Void> response = businessController.deleteEmployeeFromBusiness(testBusiness.getId(), testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testFailDeleteEmployeeFromBusiness2() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.checkExistentEmployeeById(testEmployee.getId())).thenReturn(false);

        ResponseEntity<Void> response = businessController.deleteEmployeeFromBusiness(testBusiness.getId(), testEmployee.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetPostsByBusiness() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.of(testEmployee));
        when(businessService.getEmployeesByBusiness(testBusiness.getId())).thenReturn(Collections.singletonList(testEmployee));

        ResponseEntity<Iterable<PostOutDTO>> response = businessController.getPostsByBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void testFailGetPostsByBusiness1() {
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.empty());

        ResponseEntity<Iterable<PostOutDTO>> response = businessController.getPostsByBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testFailGetPostsByBusiness2() {
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser);
        when(businessService.findBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));
        when(employeeService.findEmployeeByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<Iterable<PostOutDTO>> response = businessController.getPostsByBusiness(testBusiness.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }


}