package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Salary2Activity extends AppCompatActivity {

    private ListView salaryListView;
    private SalaryAdapter adapter;
    private ArrayList<Salary> salaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary2);

        salaryListView = findViewById(R.id.salary_list);
        Button btnBackSalary = findViewById(R.id.btn_back_salary);

        // Initialize the salary list and adapter
        salaryList = new ArrayList<>();
        adapter = new SalaryAdapter(this, salaryList);
        salaryListView.setAdapter(adapter);

        // Fetch salaries from MongoDB
        fetchSalaries();

        // Back button to return to SalaryActivity
        btnBackSalary.setOnClickListener(v -> {
            Intent intent = new Intent(Salary2Activity.this, SalaryActivity.class);
            startActivity(intent);
        });
    }

    private void fetchSalaries() {
        String url = "http://10.0.2.2:5000/api/salary";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // பதிலை பதிப்பிக்கவும்
                    Log.d("Salary2Activity", "Response: " + response.toString());

                    salaryList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String id = obj.getString("_id");
                            String employeeId = obj.getString("EmployeeID");
                            String baseSalary = obj.getString("BaseSalary");
                            String bonuses = obj.getString("Bonuses");
                            String deductions = obj.getString("Deductions");

                            salaryList.add(new Salary(id, employeeId, baseSalary, bonuses, deductions));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Log.e("Salary2Activity", "Error: " + error.getMessage());
                    Toast.makeText(Salary2Activity.this, "Error fetching salaries", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
