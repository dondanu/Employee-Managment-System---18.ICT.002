package com.example.emsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText etEmployeeID, etName, etDesignation, etSalary, etJoinDate, etContact;
    private Button btnSubmit;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Initializing the views
        etEmployeeID = findViewById(R.id.etEmployeeID);
        etName = findViewById(R.id.etName);
        etDesignation = findViewById(R.id.etDesignation);
        etSalary = findViewById(R.id.etSalary);
        etJoinDate = findViewById(R.id.etJoinDate);
        etContact = findViewById(R.id.etContact);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initializing Retrofit API interface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Button click listener for submitting the employee details
        btnSubmit.setOnClickListener(view -> {
            String employeeID = etEmployeeID.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String designation = etDesignation.getText().toString().trim();
            String salary = etSalary.getText().toString().trim();
            String joinDate = etJoinDate.getText().toString().trim();
            String contact = etContact.getText().toString().trim();

            // Convert joinDate to YYYY-MM-DD format
            joinDate = convertDateToBackendFormat(joinDate);

            // Validation: Check if any field is empty or if date conversion failed
            if (employeeID.isEmpty() || name.isEmpty() || designation.isEmpty() ||
                    salary.isEmpty() || joinDate == null || contact.isEmpty()) {
                Toast.makeText(this, "All fields are required and date must be valid", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Employee object with the input data
            Employee employee = new Employee(employeeID, name, designation, salary, joinDate, contact);

            // Call the API to add the employee
            apiInterface.addEmployee(employee).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddEmployeeActivity.this, "Employee Added Successfully", Toast.LENGTH_LONG).show();
                        finish(); // Close activity after successful submission
                    } else {
                        Toast.makeText(AddEmployeeActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AddEmployeeActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    // Method to convert date from DD/MM/YYYY to YYYY-MM-DD
    private String convertDateToBackendFormat(String inputDate) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date date = fromFormat.parse(inputDate);
            return toFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
}
