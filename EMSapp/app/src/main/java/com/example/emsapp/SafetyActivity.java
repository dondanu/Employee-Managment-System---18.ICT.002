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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SafetyActivity extends AppCompatActivity {
    private EditText edtIncidentId, edtEmployeeId, edtDescription, edtDateReported;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);

        edtIncidentId = findViewById(R.id.edt_incident_id);
        edtEmployeeId = findViewById(R.id.edt_employee_id);
        edtDescription = findViewById(R.id.edt_description);
        edtDateReported = findViewById(R.id.edt_date_reported);

        Button btnAddIncident = findViewById(R.id.btn_add_incident);
        Button btnIncidentList = findViewById(R.id.btn_incident_list);
        Button btnBackToHome = findViewById(R.id.btn_back_to_home);
        Button reset = findViewById(R.id.reset);

        RequestQueue queue = Volley.newRequestQueue(this);

        btnAddIncident.setOnClickListener(v -> {
            String incidentId = edtIncidentId.getText().toString();
            String employeeId = edtEmployeeId.getText().toString();
            String description = edtDescription.getText().toString();
            String dateReported = edtDateReported.getText().toString();

            if (incidentId.isEmpty() || employeeId.isEmpty() || description.isEmpty() || dateReported.isEmpty()) {
                Toast.makeText(SafetyActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:5000/api/safety";

            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("incidentId", incidentId);
                requestBody.put("employeeId", employeeId);
                requestBody.put("description", description);
                requestBody.put("dateReported", dateReported);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                        response -> Toast.makeText(SafetyActivity.this, "Incident Added Successfully", Toast.LENGTH_SHORT).show(),
                        error -> Toast.makeText(SafetyActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());

                queue.add(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        btnIncidentList.setOnClickListener(v -> {
            Intent intent = new Intent(SafetyActivity.this, Safety2Activity.class);
            startActivity(intent);
        });

        btnBackToHome.setOnClickListener(v -> finish());

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTask();
            }
        });
    }

    private void resetTask(){
        edtIncidentId.setText("");
        edtEmployeeId.setText("");
        edtDescription.setText("");
        edtDateReported.setText("");
    }
}
