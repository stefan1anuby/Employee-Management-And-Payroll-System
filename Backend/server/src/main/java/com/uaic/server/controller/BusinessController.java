package com.uaic.server.controller;

import com.uaic.server.entities.*;
import com.uaic.server.services.BusinessService;
import com.uaic.server.services.EmployeeService;
import com.uaic.server.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private final BusinessService businessService;
    private final EmployeeService employeeService;
    private final UserService userService;

    public BusinessController(BusinessService businessService, EmployeeService employeeService, UserService userService) {
        this.businessService = businessService;
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<BusinessOutDTO>> getBusinesses() {
        Iterable<Business> businesses = businessService.findBusinesses();

        // Map entities to DTOs
        List<BusinessOutDTO> businessDTOs = StreamSupport.stream(businesses.spliterator(), false)
                .map(this::mapToBusinessOutDTO)
                .collect(Collectors.toList());

        // Return the DTO list
        return new ResponseEntity<>(businessDTOs, HttpStatus.OK);
    }

    // TODO
    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusiness(@PathVariable UUID id) {
        Optional<Business> business = businessService.findBusinessById(id);
        if (business.isPresent()) {
            return new ResponseEntity<>(business.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<BusinessOutDTO> addBusiness(@RequestBody BusinessInDTO business) {

        // Retrieve the authenticated user's information
        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        // Check if an employee with the same email as the authenticated user already exists
        if (employeeService.findEmployeeByEmail(userInfo.getEmail()).isPresent()) {
            // Return a conflict status if the employee already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Create a new Business entity from the incoming BusinessInDTO
        Business businessToCreate = new Business(
                business.getName(),          // Set the business name
                business.getAddress(),       // Set the business address
                business.getIndustry()       // Set the business industry
        );

        // Save the new Business entity to the database
        Business businessCreated = businessService.createBusiness(businessToCreate);

        // Create a new Employee entity for the authenticated user
        Employee employeeToCreate = new Employee();
        employeeToCreate.setName(userInfo.getName());           // Set the employee's name
        employeeToCreate.setEmail(userInfo.getEmail());         // Set the employee's email
        employeeToCreate.setRole(Employee.Role.ADMIN);          // Set the employee's role as ADMIN
        employeeToCreate.setBusiness(businessCreated);          // Associate the employee with the created business

        // Save the new Employee entity to the database
        Employee employeeToReturn = employeeService.createOrUpdateEmployee(employeeToCreate);

        // Create a new BusinessOutDTO to return as the response
        BusinessOutDTO businessToReturn = new BusinessOutDTO();
        businessToReturn.setId(businessCreated.getId());        // Set the business ID
        businessToReturn.setName(businessToCreate.getName());   // Set the business name
        businessToReturn.setAddress(businessToCreate.getAddress()); // Set the business address
        businessToReturn.setIndustry(businessToCreate.getIndustry()); // Set the business industry

        // Return the BusinessOutDTO with the created business data and a CREATED HTTP status
        return new ResponseEntity<>(businessToReturn, HttpStatus.CREATED);
    }

    // TODO
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBusiness(@PathVariable UUID id, @RequestBody Business business) {
        if (businessService.checkExistentBusinessById(id)) {
            business.setId(id);
            businessService.createBusiness(business);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable UUID id) {
        if (businessService.checkExistentBusinessById(id)) {
            businessService.deleteBusinessById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{businessId}/employees")
    public ResponseEntity<Iterable<EmployeeOutDTO>> getEmployeesByBusiness(@PathVariable UUID businessId) {
        // Find the business by its ID
        Optional<Business> business = businessService.findBusinessById(businessId);

        if (business.isPresent()) {
            // Get the employees associated with the business
            Iterable<Employee> employees = businessService.getEmployeesByBusiness(businessId);

            // Convert each Employee entity to an EmployeeOutDTO
            List<EmployeeOutDTO> employeeOutDTOs = StreamSupport.stream(employees.spliterator(), false)
                    .map(this::mapToEmployeeOutDTO) // Use the helper method
                    .collect(Collectors.toList());

            // Return the list of EmployeeOutDTOs
            return new ResponseEntity<>(employeeOutDTOs, HttpStatus.OK);
        } else {
            // Return NOT_FOUND if the business doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO
    @GetMapping("/{businessId}/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByBusiness(@PathVariable UUID businessId, @PathVariable UUID employeeId) {
        Optional<Employee> employee = businessService.getEmployeeByBusinessAndEmployeeId(businessId, employeeId);
        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{businessId}/employees")
    public ResponseEntity<EmployeeOutDTO> addEmployeeToBusiness(@PathVariable UUID businessId) {

        // Retrieve the authenticated user's information (e.g., name, email)
        UserOutDTO userInfo = userService.getAuthenticatedUserInfo();

        // Look for the Business entity associated with the provided businessId
        Optional<Business> business = businessService.findBusinessById(businessId);

        // Check if the business with the given ID exists
        if (!business.isPresent()) {
            // Return a NOT_FOUND status if the business does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if an employee with the authenticated user's email already exists
        if (employeeService.findEmployeeByEmail(userInfo.getEmail()).isPresent()) {
            // Return a CONFLICT status if the employee already exists in the system
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Create a new Employee entity using the authenticated user's information
        Employee employeeToCreate = new Employee();
        employeeToCreate.setName(userInfo.getName());           // Set the employee's name
        employeeToCreate.setEmail(userInfo.getEmail());         // Set the employee's email
        employeeToCreate.setBusiness(business.get());           // Associate the employee with the found business

        // Save the newly created Employee entity to the database
        Employee employeeCreated = employeeService.createOrUpdateEmployee(employeeToCreate);
        EmployeeOutDTO employeeOutDTO = mapToEmployeeOutDTO(employeeCreated);

        // Return the created Employee object and a CREATED HTTP status
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.CREATED);
    }

    // TODO
    @PutMapping("/{businessId}/employees/{employeeId}")
    public ResponseEntity<Void> updateEmployeeInBusiness(@PathVariable UUID businessId, @PathVariable UUID employeeId, @RequestBody Employee employee) {

        // the Employee who makes this call should not be the same Employee who gets the updates, and also to edit something you have to need maybe a HR or ADMIN role?
        Optional<Business> business = businessService.findBusinessById(businessId);
        if (business.isPresent() && employeeService.checkExistentEmployeeById(employeeId)) {
            employee.setId(employeeId);
            employee.setBusiness(business.get());
            employeeService.createOrUpdateEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO
    @DeleteMapping("/{businessId}/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeFromBusiness(@PathVariable UUID businessId, @PathVariable UUID employeeId) {
        Optional<Business> business = businessService.findBusinessById(businessId);
        if (business.isPresent() && employeeService.checkExistentEmployeeById(employeeId)) {
            employeeService.deleteEmployeeById(employeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private BusinessOutDTO mapToBusinessOutDTO(Business business) {
        // Map Business to BusinessOutDTO
        BusinessOutDTO dto = new BusinessOutDTO();
        dto.setId(business.getId());
        dto.setName(business.getName());
        dto.setAddress(business.getAddress());
        dto.setIndustry(business.getIndustry());
        if (business.getEmployees() != null) {
            /*
            dto.setEmployees(business.getEmployees().stream()
                    .map(this::mapToEmployeeOutDTO)
                    .collect(Collectors.toList()));
             */
        }
        return dto;
    }

    private EmployeeOutDTO mapToEmployeeOutDTO(Employee employee) {
        EmployeeOutDTO dto = new EmployeeOutDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setRole(employee.getRole());
        dto.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getName() : null); // Handle null department
        return dto;
    }
}