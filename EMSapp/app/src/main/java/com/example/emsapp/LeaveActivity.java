package com.example.emsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LeaveActivity extends AppCompatActivity {

    private EditText edtEmployeeId, edtLeaveType, edtStartDate, edtEndDate;
    private Button btnApplyLeave, btnLeaveList, btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        edtEmployeeId = findViewById(R.id.edtEmployeeId);
        edtLeaveType = findViewById(R.id.edtLeaveType); // Updated to EditText
        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDate = findViewById(R.id.edtEndDate);
        btnApplyLeave = findViewById(R.id.btnApplyLeave);
        btnLeaveList = findViewById(R.id.btnLeaveList);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Apply Leave Button
        btnApplyLeave.setOnClickListener(v -> applyLeave());

        // Leave List Button
        btnLeaveList.setOnClickListener(v -> {
            Intent intent = new Intent(LeaveActivity.this, Leave2Activity.class);
            startActivity(intent);
        });

        // Back to Home Button
        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(LeaveActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void applyLeave() {
        String employeeId = edtEmployeeId.getText().toString();
        String leaveType = edtLeaveType.getText().toString(); // Getting text from EditText
        String startDate = edtStartDate.getText().toString();
        String endDate = edtEndDate.getText().toString();

        if (employeeId.isEmpty() || leaveType.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(LeaveActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject leaveData = new JSONObject();
        try {
            leaveData.put("employeeId", employeeId);
            leaveData.put("leaveType", leaveType);
            leaveData.put("startDate", startDate);
            leaveData.put("endDate", endDate);

            String url = "http://10.0.2.2:5000/api/leaves"; // Adjust URL for actual backend

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, leaveData,
                    response -> {
                        Toast.makeText(LeaveActivity.this, "Leave applied successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> Toast.makeText(LeaveActivity.this, "Error applying leave", Toast.LENGTH_SHORT).show());

            RequestQueue queue = Volley.newRequestQueue(LeaveActivity.this);
            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
