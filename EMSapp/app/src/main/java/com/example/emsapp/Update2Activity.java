package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update2Activity extends AppCompatActivity {

    private EditText etEmployeeID, etName, etDesignation, etDepartment, etJoiningDate, etEmail;
    private Button btnUpdate, btnBackToHome;
    private ApiInterface apiInterface;
    private String employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update2);

        // Initialize Views
        etEmployeeID = findViewById(R.id.etEmployeeID);
        etName = findViewById(R.id.etName);
        etDesignation = findViewById(R.id.etDesignation);
        etDepartment = findViewById(R.id.etDepartment);
        etJoiningDate = findViewById(R.id.etJoiningDate);
        etEmail = findViewById(R.id.etEmail);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Get employee details passed from UpdateEmployeeActivity
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("employeeID");
        String name = intent.getStringExtra("name");
        String designation = intent.getStringExtra("designation");
        String department = intent.getStringExtra("department");
        String joiningDate = intent.getStringExtra("joiningDate");
        String email = intent.getStringExtra("email");

        // Populate EditTexts with existing data
        etEmployeeID.setText(employeeID);
        etName.setText(name);
        etDesignation.setText(designation);
        etDepartment.setText(department);
        etJoiningDate.setText(joiningDate);
        etEmail.setText(email);

        // Initialize API Interface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Update button click listener
        btnUpdate.setOnClickListener(view -> {
            String updatedName = etName.getText().toString();
            String updatedDesignation = etDesignation.getText().toString();
            String updatedDepartment = etDepartment.getText().toString();
            String updatedJoiningDate = etJoiningDate.getText().toString();
            String updatedEmail = etEmail.getText().toString();

            // Create the updated Employee object
            Employee updatedEmployee = new Employee(employeeID, updatedName, updatedDesignation, updatedDepartment, updatedJoiningDate, updatedEmail);

            // Call API to update the employee
            updateEmployeeDetails(updatedEmployee);
        });

        // Back to Home button click listener
        btnBackToHome.setOnClickListener(view -> {
            Intent backIntent = new Intent(Update2Activity.this, UpdateEmployeeActivity.class);
            startActivity(backIntent);
        });
    }

    private void updateEmployeeDetails(Employee updatedEmployee) {
        apiInterface.updateEmployee(updatedEmployee.getEmployeeID(), updatedEmployee).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Update2Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    // Go back to UpdateEmployeeActivity
                    startActivity(new Intent(Update2Activity.this, UpdateEmployeeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Update2Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
