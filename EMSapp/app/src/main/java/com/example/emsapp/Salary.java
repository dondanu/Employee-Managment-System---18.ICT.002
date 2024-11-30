package com.example.emsapp;

public class Salary {
    private String id;          // MongoDB record ID
    private String employeeId;  // Employee ID
    private String baseSalary;  // Base Salary
    private String bonuses;     // Bonuses
    private String deductions;  // Deductions

    // Constructor
    public Salary(String id, String employeeId, String baseSalary, String bonuses, String deductions) {
        this.id = id;
        this.employeeId = employeeId;
        this.baseSalary = baseSalary;
        this.bonuses = bonuses;
        this.deductions = deductions;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(String baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getBonuses() {
        return bonuses;
    }

    public void setBonuses(String bonuses) {
        this.bonuses = bonuses;
    }

    public String getDeductions() {
        return deductions;
    }

    public void setDeductions(String deductions) {
        this.deductions = deductions;
    }
}
