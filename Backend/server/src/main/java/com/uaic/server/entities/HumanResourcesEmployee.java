/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.entities;

import java.util.List;

/**
 *
 * @author G
 */
public class HumanResourcesEmployee extends Employee{
    
    List<Employee> employeesList;
    
    
    // Aici folosesc constructor injection, declarand dependenta List<Employee> ca parametru al contructorului.
    public HumanResourcesEmployee(Integer id, String name, String email, String role, String department, int salary, List<Employee> list) {
        super(id, name, email, role, department, salary);
        this.employeesList = list;
    }

    
    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    // Aici folosesc setter injection, declarand dependenta List<Employee> ca parametru al setter-ului.
    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }
    
    // urmeaza sa folosesc Observer aici, pentru a notifica ceilalti angajati in legatura cu anunturile
    public void postAnnouncement()
    {
        
    }
    
}
