package com.example.emsapp;

public class Attendance {
    private String id;            // MongoDB _id
    private String employeeId;
    private String date;
    private String checkIn;
    private String checkOut;
    private String status;

    // Constructor to include 'id' for the MongoDB _id field
    public Attendance(String id, String employeeId, String date, String checkIn, String checkOut, String status) {
        this.id = id;              // Initialize the _id field
        this.employeeId = employeeId;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }          // Getter for _id
    public String getEmployeeId() { return employeeId; }
    public String getDate() { return date; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
    public String getStatus() { return status; }
}
