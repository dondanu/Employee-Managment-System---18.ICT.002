package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeeActivity extends AppCompatActivity {

    private RecyclerView rvEmployees;
    private Button btnBackToHome;
    private ApiInterface apiInterface;
    private UpdateEmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        // Initialize Views
        rvEmployees = findViewById(R.id.rvEmployees);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // API Interface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Back to Home Button
        btnBackToHome.setOnClickListener(view -> {
            Intent intent = new Intent(UpdateEmployeeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Fetch and Display Employee Data
        fetchEmployeeData();
    }

    private void fetchEmployeeData() {
        apiInterface.getEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> employeeList = response.body();
                    setupRecyclerView(employeeList);
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void setupRecyclerView(List<Employee> employeeList) {
        adapter = new UpdateEmployeeAdapter(this, employeeList);
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        rvEmployees.setAdapter(adapter);
    }
}
