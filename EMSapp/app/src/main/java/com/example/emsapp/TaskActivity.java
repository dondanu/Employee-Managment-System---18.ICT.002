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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class TaskActivity extends AppCompatActivity {

    private EditText etEmployeeId, etTitle, etDescription, etAssignedTo, etStartDate, etEndDate, etPriority;
    private Button btnAddTask, btnTaskRecords, btnBackToHome,reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // UI அமைப்பு
        etEmployeeId = findViewById(R.id.et_employee_id);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        etAssignedTo = findViewById(R.id.et_assigned_to);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);
        etPriority = findViewById(R.id.et_priority);

        btnAddTask = findViewById(R.id.btn_add_task);
        btnTaskRecords = findViewById(R.id.btn_task_records);
        btnBackToHome = findViewById(R.id.btn_back_to_home);
        reset = findViewById(R.id.reset);

        // "Add Task" பொத்தானை அழுத்தும்போது
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        // "Task Records" பொத்தானை அழுத்தும்போது Task2Activityக்கு செல்ல
        btnTaskRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, Task2Activity.class);
                startActivity(intent);
            }
        });

        // "Back to Home" பொத்தானை அழுத்தும்போது MainActivityக்கு செல்ல
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeId = etEmployeeId.getText().toString();
                resetTask();
            }
        });
    }

    private void resetTask(){
        etEmployeeId.setText("");
        etTitle.setText("");
        etDescription.setText("");
        etAssignedTo.setText("");
        etStartDate.setText("");
        etEndDate.setText("");
        etPriority.setText("");
    }

    // Add Task சேமிப்பதற்கான செயல்
    private void addTask() {
        String employeeId = etEmployeeId.getText().toString();
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String assignedTo = etAssignedTo.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String priority = etPriority.getText().toString();

        if (employeeId.isEmpty() || title.isEmpty() || description.isEmpty()) {
            Toast.makeText(TaskActivity.this, "தயவுசெய்து அனைத்து புலங்களையும் பூர்த்தி செய்யவும்", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskId", employeeId);  // Task ID can be same as Employee ID
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("assignedTo", assignedTo);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", endDate);
            jsonObject.put("priority", priority);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "http://10.0.2.2:5000/api/tasks";  // Change this URL to your server URL
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(TaskActivity.this, "Task வெற்றிகரமாக சேர்க்கப்பட்டது!", Toast.LENGTH_SHORT).show();
        }, error -> {
            Toast.makeText(TaskActivity.this, "Task சேர்க்க பிழை: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        requestQueue.add(stringRequest);
    }
}
