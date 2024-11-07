/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uaic.server.model;

/**
 *
 * @author G
 */
public class Manager extends Employee {

    String team;
    String companyBankAccount;

    public Manager(Integer id, String name, String email, String role, String department, int salary, String team,
            String bankAccount) {
        super(id, name, email, role, department, salary);
        this.team = team;
        this.companyBankAccount = bankAccount;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCompanyBankAccount() {
        return companyBankAccount;
    }

    public void setCompanyBankAccount(String companyBankAccount) {
        this.companyBankAccount = companyBankAccount;
    }

    // urmeaza sa folosesc Observer aici, pentru a notifica ceilalti angajati in
    // legatura cu anunturile
    public void postAnnouncement() {

    }

    public void promoteEmployee() {

    }

}
