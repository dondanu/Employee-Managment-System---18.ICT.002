package com.example.emsapp;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("EmployeeID")
    private String employeeID;

    @SerializedName("Name")
    private String name;

    @SerializedName("Designation")
    private String designation;

    @SerializedName("Salary")
    private String salary;

    @SerializedName("JoinDate")
    private String joinDate;

    @SerializedName("Contact")
    private String contact;

    public Employee(String employeeID, String name, String designation, String salary, String joinDate, String contact) {
        this.employeeID = employeeID;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.joinDate = joinDate;
        this.contact = contact;
    }

    // Getters and setters (optional if needed)
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
