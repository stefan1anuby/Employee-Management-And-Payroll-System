/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author G
 */
@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private BigDecimal salary;
    private String team;

    // Enum for role with mapping to SQL ENUM type
    @Enumerated(EnumType.STRING)
    private Role role;

    // List of managed employee IDs for HR employees
    @ElementCollection
    @CollectionTable(name = "employee_managed_ids", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "managed_employee_id")
    private List<Integer> managedEmployeeIds;

    // Enum for role with possible values
    public enum Role {
        MANAGER, HR
    }

    // Constructors, getters, setters, and business methods

    public Employee() {
    }

    public Employee(Integer id, String name, String email, String phoneNumber, Role role,
            Department department, BigDecimal salary, String team,
            List<Integer> managedEmployeeIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.department = department;
        this.salary = salary;
        this.team = team;
        this.managedEmployeeIds = managedEmployeeIds;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setManagedEmployeeIds(List<Integer> managedEmployeeIds) {
        this.managedEmployeeIds = managedEmployeeIds;
    }

    // Business methods
    public void giveFeedback() {
        // Implementation here
    }

    public void receiveFeedback() {
        // Implementation here
    }

    public void readAnnouncement() {
        // Implementation here
    }
}
