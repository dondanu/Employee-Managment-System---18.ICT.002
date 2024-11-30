package com.example.emsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class AttendanceActivity extends Activity {
    private EditText edtEmployeeId, edtDate, edtCheckIn, edtCheckOut, edtStatus;
    private Button btnAddAttendance, btnAttendanceRecords, btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        edtEmployeeId = findViewById(R.id.edtEmployeeId);
        edtDate = findViewById(R.id.edtDate);
        edtCheckIn = findViewById(R.id.edtCheckIn);
        edtCheckOut = findViewById(R.id.edtCheckOut);
        edtStatus = findViewById(R.id.edtStatus);

        btnAddAttendance = findViewById(R.id.btnAddAttendance);
        btnAttendanceRecords = findViewById(R.id.btnAttendanceRecords);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Add Attendance logic
        btnAddAttendance.setOnClickListener(v -> addAttendance());

        // Open Attendance Records
        btnAttendanceRecords.setOnClickListener(v -> openAttendanceRecords());

        // Back to Home
        btnBackToHome.setOnClickListener(v -> finish());
    }

    private void addAttendance() {
        String url = "http://10.0.2.2:5000/api/attendance"; // Ensure correct backend URL

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("EmployeeID", edtEmployeeId.getText().toString());
            jsonRequest.put("Date", edtDate.getText().toString());
            jsonRequest.put("CheckInTime", edtCheckIn.getText().toString());
            jsonRequest.put("CheckOutTime", edtCheckOut.getText().toString());
            jsonRequest.put("Status", edtStatus.getText().toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    response -> {
                        Toast.makeText(AttendanceActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Toast.makeText(AttendanceActivity.this, "Added Failed", Toast.LENGTH_SHORT).show();
                    });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAttendanceRecords() {
        Intent intent = new Intent(AttendanceActivity.this, Attendance2Activity.class);
        startActivity(intent);
    }


}
