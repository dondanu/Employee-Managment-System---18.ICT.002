package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteEmployeeActivity extends AppCompatActivity {

    private RecyclerView rvEmployees;
    private Button btnBackToHome;
    private ApiInterface apiInterface;
    private DeleteEmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        rvEmployees = findViewById(R.id.rvEmployees);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btnBackToHome.setOnClickListener(view -> {
            Intent intent = new Intent(DeleteEmployeeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        fetchEmployeeData();
    }

    private void fetchEmployeeData() {
        apiInterface.getEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setupRecyclerView(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(DeleteEmployeeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecyclerView(List<Employee> employeeList) {
        adapter = new DeleteEmployeeAdapter(this, employeeList, employee -> {
            apiInterface.deleteEmployee(employee.getEmployeeID()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(DeleteEmployeeActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    fetchEmployeeData();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(DeleteEmployeeActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        rvEmployees.setAdapter(adapter);
    }
}
