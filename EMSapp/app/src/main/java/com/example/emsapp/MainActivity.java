package com.example.emsapp;

import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnAddEmployee, btnUpdateEmployee, btnDeleteEmployee, btnViewEmployees;
    //private Button btnAttendanceEmployee;
    private Button btnTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add Employee Part
        btnAddEmployee = findViewById(R.id.btnAddEmployee);
        btnUpdateEmployee = findViewById(R.id.btnUpdateEmployee);
        btnDeleteEmployee = findViewById(R.id.btnDeleteEmployee);
        btnViewEmployees = findViewById(R.id.btnViewEmployees);

        //Attendance Part
        //btnAttendanceEmployee = findViewById(R.id.btnAttendanceEmployee);

        btnAddEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
            startActivity(intent);
        });

        btnUpdateEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UpdateEmployeeActivity.class);
            startActivity(intent);
        });

        btnDeleteEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DeleteEmployeeActivity.class);
            startActivity(intent);
        });

        btnViewEmployees.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ViewEmployeesActivity.class);
            startActivity(intent);
        });

        Button viewAttendanceButton = findViewById(R.id.viewAttendanceButton);
        viewAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        Button btnWelfare = findViewById(R.id.btn_welfare);
        btnWelfare.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WelfareActivity.class);
            startActivity(intent);
        });

        btnTask = findViewById(R.id.btn_task);  // Task Button (MainActivityல் இருக்கும்)

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivity இல் Task button அழுத்தியவுடன் TaskActivityக்கு செல்ல
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

        // Button to open SafetyActivity
        Button safetyButton = findViewById(R.id.btn_safety);
        safetyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SafetyActivity.class);
            startActivity(intent);
        });

        Button btnSalary = findViewById(R.id.btn_salary);
        btnSalary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SalaryActivity.class);
            startActivity(intent);
        });

        // MainActivity.java
        Button btnLeave = findViewById(R.id.btnLeave);
        btnLeave.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LeaveActivity.class);
            startActivity(intent);
        });

        // Find the TextView by its ID
        TextView githubText = findViewById(R.id.githubText);

        // Set the click listener to open GitHub URL
        githubText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGitHub();
            }
        });

    }
    // This method will open the GitHub URL when the TextView is clicked
    public void openGitHub() {
        // Define the GitHub URL
        String githubUrl = "https://github.com/dondanu";

        // Create an implicit intent to open the URL in a browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));

        // Start the activity with the intent
        startActivity(intent);
    }

    }

