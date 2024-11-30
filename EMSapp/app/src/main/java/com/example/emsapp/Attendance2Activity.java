package com.example.emsapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Attendance2Activity extends Activity {
    private Button btnBackToAttendance;
    private ListView lvAttendanceRecords;
    private ArrayList<Attendance> attendanceList;
    private AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance2);

        btnBackToAttendance = findViewById(R.id.btnBackToAttendance);
        lvAttendanceRecords = findViewById(R.id.attendanceListView);

        btnBackToAttendance.setOnClickListener(v -> finish());

        attendanceList = new ArrayList<>();
        adapter = new AttendanceAdapter(this, attendanceList);
        lvAttendanceRecords.setAdapter(adapter);

        fetchAttendanceRecords();
    }

    private void fetchAttendanceRecords() {
        String url = "http://10.0.2.2:5000/api/attendance";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    attendanceList.clear(); // Clear previous data to avoid duplicates
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            // Ensure _id is included in the Attendance object creation
                            Attendance attendance = new Attendance(
                                    obj.getString("_id"), // MongoDB _id
                                    obj.getString("EmployeeID"),
                                    obj.getString("Date"),
                                    obj.getString("CheckInTime"),
                                    obj.getString("CheckOutTime"),
                                    obj.getString("Status")
                            );
                            attendanceList.add(attendance);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(Attendance2Activity.this, "Error Fetching Records", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
