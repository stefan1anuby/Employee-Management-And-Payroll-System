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
    
    public HumanResourcesEmployee(Integer id, String name, String email, String role, String department, int salary, List<Employee> list) {
        super(id, name, email, role, department, salary);
        this.employeesList = list;
    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }
    
    public void postAnnouncement()
    {
        
    }
    
}
