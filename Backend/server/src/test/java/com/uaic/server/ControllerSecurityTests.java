package com.uaic.server;

import com.uaic.server.controller.BusinessController;
import com.uaic.server.entities.*;
import com.uaic.server.services.BusinessService;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ControllerSecurityTests {

    @InjectMocks
    private BusinessController businessController;

    @Mock
    private BusinessService businessService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private UserService userService;

    private UserOutDTO testUser1;
    private UserOutDTO testUser2;
    private BusinessInDTO testBusiness1;
    private BusinessInDTO testBusiness2;
    private Business testCreatedBusiness1;
    private Business testCreatedBusiness2;
    private Employee testEmployee1;
    private Employee testEmployee2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a test business input DTO
        testBusiness1 = new BusinessInDTO();
        testBusiness1.setName("Test Business");
        testBusiness1.setAddress("123 Business St");
        testBusiness1.setIndustry("Tech");

        // Initialize a test created business entity
        testCreatedBusiness1 = new Business();
        testCreatedBusiness1.setId(UUID.randomUUID());
        testCreatedBusiness1.setName(testBusiness1.getName());
        testCreatedBusiness1.setAddress(testBusiness1.getAddress());
        testCreatedBusiness1.setIndustry(testBusiness1.getIndustry());

        // Initialize test user 1
        testUser1 = new UserOutDTO();
        UUID user1Id = UUID.randomUUID();
        testUser1.setUserId(user1Id.toString());
        testUser1.setName("Test User 1");
        testUser1.setEmail("testuser1@gmail.com");
        testUser1.setRegisterDate(LocalDateTime.now().minusDays(1));
        testUser1.setExpirationDate(LocalDateTime.now().plusYears(1));

        // Initialize test employee 1
        testEmployee1 = new Employee();
        testEmployee1.setId(user1Id);
        testEmployee1.setName(testUser1.getName());
        testEmployee1.setEmail(testUser1.getEmail());
        testEmployee1.setRole(Employee.Role.ADMIN);
        testEmployee1.setBusiness(testCreatedBusiness1);



        // Initialize a test business input DTO
        testBusiness2 = new BusinessInDTO();
        testBusiness2.setName("Test2 Business");
        testBusiness2.setAddress("1234 Business St");
        testBusiness2.setIndustry("Techc");

        // Initialize a test created business entity
        testCreatedBusiness2 = new Business();
        testCreatedBusiness2.setId(UUID.randomUUID());
        testCreatedBusiness2.setName(testBusiness2.getName());
        testCreatedBusiness2.setAddress(testBusiness2.getAddress());
        testCreatedBusiness2.setIndustry(testBusiness2.getIndustry());

        // Initialize test user 1
        testUser2 = new UserOutDTO();
        UUID user2Id = UUID.randomUUID();
        testUser2.setUserId(user2Id.toString());
        testUser2.setName("Test User 2");
        testUser2.setEmail("testuser2@gmail.com");
        testUser2.setRegisterDate(LocalDateTime.now().minusDays(1));
        testUser2.setExpirationDate(LocalDateTime.now().plusYears(1));

        // Initialize test employee 1
        testEmployee2 = new Employee();
        testEmployee2.setId(user2Id);
        testEmployee2.setName(testUser2.getName());
        testEmployee2.setEmail(testUser2.getEmail());
        testEmployee2.setRole(Employee.Role.ADMIN);
        testEmployee2.setBusiness(testCreatedBusiness2);
    }

    @Test
    void testAccessBusinessByDifferentBusinessEmployee() {
        // Mock the authenticated user as testUser1
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser1);

        // Mock the check for existing employees (none should exist for this test)
        when(employeeService.findEmployeeByEmail(testUser1.getEmail())).thenReturn(Optional.empty());

        // Mock the business creation service
        when(businessService.createBusiness(any(Business.class))).thenReturn(testCreatedBusiness1);

        // Call the controller method to create a business
        ResponseEntity<BusinessOutDTO> response = businessController.createBusiness(testBusiness1);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testBusiness1.getName(), response.getBody().getName());

        // Verify interactions
        verify(userService, times(1)).getAuthenticatedUserInfo();
        verify(employeeService, times(1)).findEmployeeByEmail(testUser1.getEmail());
        verify(businessService, times(1)).createBusiness(any(Business.class));
        verify(employeeService, times(1)).createOrUpdateEmployee(any(Employee.class));

        // check if the connected employee can get the associated business info
        when(employeeService.findEmployeeByEmail(testUser1.getEmail())).thenReturn(Optional.ofNullable(testEmployee1));
        when(businessService.findBusinessById(testCreatedBusiness1.getId())).thenReturn(Optional.ofNullable(testCreatedBusiness1));
        ResponseEntity<BusinessOutDTO> response2 = businessController.getBusiness(response.getBody().getId());

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        // check if another employee can get the another business info
        when(userService.getAuthenticatedUserInfo()).thenReturn(testUser2);
        when(employeeService.findEmployeeByEmail(testUser2.getEmail())).thenReturn(Optional.ofNullable(testEmployee2));
        when(businessService.findBusinessById(testCreatedBusiness1.getId())).thenReturn(Optional.ofNullable(testCreatedBusiness1));
        ResponseEntity<BusinessOutDTO> response3 = businessController.getBusiness(response.getBody().getId());

        assertEquals(HttpStatus.FORBIDDEN, response3.getStatusCode());
    }
}
