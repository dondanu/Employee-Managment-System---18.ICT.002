package com.example.emsapp;

import java.util.Date;

public class SafetyIncident {

    private String incidentId;
    private String employeeId;
    private String description;
    private Date dateReported;
    private String status;

    // Constructor
    public SafetyIncident(String incidentId, String employeeId, String description, String status) {
        this.incidentId = incidentId;
        this.employeeId = employeeId;
        this.description = description;
        this.dateReported = dateReported;
        this.status = status;
    }

    // Getter and Setter methods
    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
