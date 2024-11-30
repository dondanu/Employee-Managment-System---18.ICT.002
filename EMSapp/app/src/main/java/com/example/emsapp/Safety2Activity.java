package com.example.emsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Safety2Activity extends AppCompatActivity {
    private static final String TAG = "Safety2Activity";
    private ListView listView;
    private ArrayList<SafetyIncident> incidentList;
    private IncidentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety2);

        // Initialize views
        try {
            listView = findViewById(R.id.incident_list_view);
            Button btnBack = findViewById(R.id.btn_back);

            // Initialize list and adapter
            incidentList = new ArrayList<>();
            adapter = new IncidentAdapter(this, incidentList);
            listView.setAdapter(adapter);

            // Fetch incidents
            fetchIncidents();

            // Back button functionality
            btnBack.setOnClickListener(v -> finish());
        } catch (Exception e) {
            Log.e(TAG, "Initialization Error: " + e.getMessage());
        }
    }

    private void fetchIncidents() {
        String url = "http://10.0.2.2:5000/api/safety"; // Adjust if needed

        Log.d(TAG, "Fetching data from: " + url);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response received: " + response.toString());
                        try {
                            incidentList.clear(); // Clear existing data

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);

                                // Safely parse JSON
                                String incidentId = obj.optString("incidentId", "Unknown");
                                String employeeId = obj.optString("employeeId", "Unknown");
                                String description = obj.optString("description", "No description");
                                String dateReported = obj.optString("dateReported", "N/A");

                                // Add to list
                                incidentList.add(new SafetyIncident(incidentId, employeeId, description, dateReported));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON Parsing Error: " + e.getMessage());
                            Toast.makeText(Safety2Activity.this, "Error parsing data!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley Error: " + error.toString());
                        Toast.makeText(Safety2Activity.this, "Network error. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(request);
    }
}
