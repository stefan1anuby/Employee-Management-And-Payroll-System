/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.services;

import com.uaic.server.entities.Department;
import com.uaic.server.repositories.DepartmentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author G
 */
public class DepartmentService {
    
     @Autowired
    private DepartmentRepository departmentRepository;
    
    public Department createOrUpdateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    

    public boolean checkExistentDepartmentById(Integer id) {
        return departmentRepository.existsById(id);
    }

    public boolean checkExistentName(String name) {
        return departmentRepository.existsByName(name);
    }

    
    public Optional<Department> findDepartmentById(Integer id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    

    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    public void deleteDepartmentById(Integer id) {
        departmentRepository.deleteById(id);
    }

    public void deleteDepartmentByName(String name) {
        departmentRepository.deleteByName(name);
    }

    public void deleteDepartmentByLocation(String location) {
        departmentRepository.deleteByLocation(location);
    }

   
    public void deleteDepartments(Iterable<Department> departments) {
        departmentRepository.deleteAll(departments);
    }


    public void clearDatabase() {
        departmentRepository.deleteAll();
    }

    public long numberOfDepartments() {
        return departmentRepository.count();
    }
}
