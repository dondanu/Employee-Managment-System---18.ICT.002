package com.example.emsapp;

// Leave2Activity.java
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Leave2Activity extends AppCompatActivity {

    private ListView leaveListView;
    private Button btnBackToLeave;
    private LeaveAdapter leaveAdapter;
    private ArrayList<Leave> leaveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave2);

        leaveListView = findViewById(R.id.leaveListView);
        btnBackToLeave = findViewById(R.id.btnBackToLeave);

        leaveList = new ArrayList<>();
        leaveAdapter = new LeaveAdapter(this, leaveList);
        leaveListView.setAdapter(leaveAdapter);

        // Fetch leave data from MongoDB
        fetchLeaves();

        // Navigate back to LeaveActivity
        btnBackToLeave.setOnClickListener(v -> {
            Intent intent = new Intent(Leave2Activity.this, LeaveActivity.class);
            startActivity(intent);
        });

        // Handle item clicks for deleting a leave
        leaveListView.setOnItemClickListener((parent, view, position, id) -> {
            Leave selectedLeave = leaveList.get(position);
            deleteLeave(selectedLeave.getLeaveId());  // Use leaveId for delete request
        });
    }

    private void fetchLeaves() {
        String url = "http://10.0.2.2:5000/api/leaves"; // Ensure correct backend URL

        // Create a JsonArrayRequest to fetch all leave data
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Clear previous data
                        leaveList.clear();

                        // Parse the response and add to the list
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject leaveObj = response.getJSONObject(i);
                            String leaveId = leaveObj.getString("_id");
                            String employeeId = leaveObj.getString("employeeId");
                            String leaveType = leaveObj.getString("leaveType");
                            String startDate = leaveObj.getString("startDate");
                            String endDate = leaveObj.getString("endDate");

                            Leave leave = new Leave(leaveId, employeeId, leaveType, startDate, endDate);
                            leaveList.add(leave);
                        }

                        // Notify adapter about the data change
                        leaveAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Leave2Activity.this, "Error parsing leave data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Log error and show error message for debugging
                    error.printStackTrace();
                    Toast.makeText(Leave2Activity.this, "Error fetching leaves", Toast.LENGTH_SHORT).show();
                });

        // Make the request
        RequestQueue queue = Volley.newRequestQueue(Leave2Activity.this);
        queue.add(request);
    }

    private void deleteLeave(String employeeId) {
        // Construct the correct URL with employeeId (ensure this matches the backend route)
        String url = "http://10.0.2.2:5000/api/leaves/" + employeeId;  // Use employeeId for deletion

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    // Show success message
                    Toast.makeText(Leave2Activity.this, "Leave deleted successfully", Toast.LENGTH_SHORT).show();
                    // Refresh the list after deletion
                    fetchLeaves();
                },
                error -> {
                    // Log error and show error message for debugging
                    error.printStackTrace();
                    Toast.makeText(Leave2Activity.this, "Error deleting leave", Toast.LENGTH_SHORT).show();
                });

        // Make the delete request
        RequestQueue queue = Volley.newRequestQueue(Leave2Activity.this);
        queue.add(request);
    }

}
