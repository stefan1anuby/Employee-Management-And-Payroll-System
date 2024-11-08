/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uaic.server.repositories;
import com.uaic.server.entities.Employee;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author G
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Find by role
    List<Employee> findByRole(Employee.Role role);

    // Find by email (useful if emails are unique)
    Optional<Employee> findByEmail(String email);

//    // Custom method to find employees by department
//    List<Employee> findByDepartment(String department);

    // Find by phone number
    Optional<Employee> findByPhoneNumber(String phoneNumber);

    
    // Find by team
    List<Employee> findByTeam(String team);

    // Find all employees with a specific salary or greater
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);

    // Custom method to find employees managed by a specific manager
    List<Employee> findByManagedEmployeeIdsContains(Integer managedEmployeeId);

    public boolean existsByName(String name);

    public boolean existsByEmail(String email);

    public List<Employee> findByName(String name);

    public void deleteByName(String name);

    public void deleteByEmail(String email);

    
}
