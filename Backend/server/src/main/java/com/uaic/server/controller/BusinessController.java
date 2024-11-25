package com.uaic.server.controller;

import com.uaic.server.entities.Business;
import com.uaic.server.entities.Employee;
import com.uaic.server.services.BusinessService;
import com.uaic.server.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Iterable<Business>> getBusinesses() {
        Iterable<Business> businesses = businessService.findBusinesses();
        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusiness(@PathVariable Long id) {
        Optional<Business> business = businessService.findBusinessById(id);
        if (business.isPresent()) {
            return new ResponseEntity<>(business.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addBusiness(@RequestBody Business business) {
        businessService.createBusiness(business);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        if (businessService.checkExistentBusinessById(id)) {
            business.setId(id);
            businessService.createBusiness(business);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        if (businessService.checkExistentBusinessById(id)) {
            businessService.deleteBusinessById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{businessId}/employees")
    public ResponseEntity<Iterable<Employee>> getEmployeesByBusiness(@PathVariable Long businessId) {
        Optional<Business> business = businessService.findBusinessById(businessId);
        if (business.isPresent()) {
            Iterable<Employee> employees = businessService.getEmployeesByBusiness(businessId);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{businessId}/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeByBusiness(@PathVariable Long businessId, @PathVariable Integer employeeId) {
        Optional<Employee> employee = businessService.getEmployeeByBusinessAndEmployeeId(businessId, employeeId);
        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{businessId}/employees")
    public ResponseEntity<Void> addEmployeeToBusiness(@PathVariable Long businessId, @RequestBody Employee employee) {
        Optional<Business> business = businessService.findBusinessById(businessId);
        if (business.isPresent()) {
            employee.setBusiness(business.get());
            employeeService.createOrUpdateEmployee(employee);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{businessId}/employees/{employeeId}")
    public ResponseEntity<Void> updateEmployeeInBusiness(@PathVariable Long businessId, @PathVariable Integer employeeId, @RequestBody Employee employee) {
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

    @DeleteMapping("/{businessId}/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeFromBusiness(@PathVariable Long businessId, @PathVariable Integer employeeId) {
        Optional<Business> business = businessService.findBusinessById(businessId);
        if (business.isPresent() && employeeService.checkExistentEmployeeById(employeeId)) {
            employeeService.deleteEmployeeById(employeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}