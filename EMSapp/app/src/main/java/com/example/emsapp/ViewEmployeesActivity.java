package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEmployeesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private Button btnBackToHome;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // RecyclerView settings
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Fetch employees
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        fetchEmployeeData();

        // Back to Home button click listener
        btnBackToHome.setOnClickListener(view -> {
            Intent intent = new Intent(ViewEmployeesActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchEmployeeData() {
        apiInterface.getEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    employeeAdapter = new EmployeeAdapter(ViewEmployeesActivity.this, response.body());
                    recyclerView.setAdapter(employeeAdapter);
                } else {
                    Toast.makeText(ViewEmployeesActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(ViewEmployeesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
