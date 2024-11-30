package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SalaryActivity extends AppCompatActivity {
    private EditText etEmployeeId, etBaseSalary, etBonuses, etDeductions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        // Initialize Views
        etEmployeeId = findViewById(R.id.et_employee_id);
        etBaseSalary = findViewById(R.id.et_base_salary);
        etBonuses = findViewById(R.id.et_bonuses);
        etDeductions = findViewById(R.id.et_deductions);

        Button btnAddSalary = findViewById(R.id.btn_add_salary);
        Button btnViewSalaries = findViewById(R.id.btn_view_salaries);
        Button btnBackToHome = findViewById(R.id.btn_back_to_home);

        // Button Click Listeners
        btnAddSalary.setOnClickListener(v -> addSalary());
        btnViewSalaries.setOnClickListener(v -> {
            Intent intent = new Intent(SalaryActivity.this, Salary2Activity.class);
            startActivity(intent);
        });
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(SalaryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void addSalary() {
        String url = "http://10.0.2.2:5000/api/salary";

        // Get Input Values
        String employeeId = etEmployeeId.getText().toString().trim();
        String baseSalary = etBaseSalary.getText().toString().trim();
        String bonuses = etBonuses.getText().toString().trim();
        String deductions = etDeductions.getText().toString().trim();

        // Validation
        if (employeeId.isEmpty() || baseSalary.isEmpty() || bonuses.isEmpty() || deductions.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double base = Double.parseDouble(baseSalary);
            double bonus = Double.parseDouble(bonuses);
            double deduction = Double.parseDouble(deductions);
            double finalSalary = base + bonus - deduction; // Final Salary Calculation

            // Create JSON body
            JSONObject salaryJson = new JSONObject();
            salaryJson.put("EmployeeID", employeeId);
            salaryJson.put("BaseSalary", base);
            salaryJson.put("Bonuses", bonus);
            salaryJson.put("Deductions", deduction);
            salaryJson.put("FinalSalary", finalSalary);

            // API Request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, salaryJson,
                    response -> {
                        Log.d("SalaryActivity", "Response: " + response.toString());
                        Toast.makeText(SalaryActivity.this, "Salary Added Successfully!", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Log.e("SalaryActivity", "Error: " + error.getMessage());
                        Toast.makeText(SalaryActivity.this, "Error: Unable to add salary", Toast.LENGTH_SHORT).show();
                    });

            // Add Request to Queue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);

        } catch (NumberFormatException | JSONException e) {
            Toast.makeText(this, "Invalid input or error in JSON creation", Toast.LENGTH_SHORT).show();
            Log.e("SalaryActivity", "Error: " + e.getMessage());
        }
    }


}
