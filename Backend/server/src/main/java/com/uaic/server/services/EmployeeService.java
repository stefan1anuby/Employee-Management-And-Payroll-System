/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.services;

import com.uaic.server.entities.Employee;
import org.springframework.stereotype.Service;
import com.uaic.server.repositories.EmployeeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author G
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee createOrUpdateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean checkExistentEmployeeById(Integer id) {
        return employeeRepository.existsById(id);
    }

    public boolean checkExistentName(String name) {
        return employeeRepository.existsByName(name);
    }

    public boolean checkExistentEmployeeByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }


    public Optional<Employee> findEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Optional<Employee> findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    

    public Iterable<Employee> findEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public void deleteEmployeeByName(String name) {
        employeeRepository.deleteByName(name);
    }

    public void deleteEmployeeByEmail(String email) {
        employeeRepository.deleteByEmail(email);
    }

   
    public void deleteUsers(Iterable<Employee> employees) {
        employeeRepository.deleteAll(employees);
    }

    public void deleteEmployeesById(Iterable<Integer> ids) {
        employeeRepository.deleteAllById(ids);
    }

    public void clearDatabase() {
        employeeRepository.deleteAll();
    }

    public long numberOfEmployees() {
        return employeeRepository.count();
    }
    
}
