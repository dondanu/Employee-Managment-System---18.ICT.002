package com.example.emsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Task2Activity extends AppCompatActivity {

    private Button btnBack;
    private ListView taskListView;  // To show task records
    private TaskAdapter taskAdapter;  // Adapter to populate ListView
    private ArrayList<Task> taskList = new ArrayList<>();  // Task list to hold task data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        // Initialize UI elements
        btnBack = findViewById(R.id.btn_back);
        taskListView = findViewById(R.id.task_list_view);

        // Set up the adapter
        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);

        // Fetch tasks from server (API call)
        fetchTasks();

        // Back button action to go back to TaskActivity
        btnBack.setOnClickListener(view -> finish());
    }

    private void fetchTasks() {
        // URL to get all tasks from server
        String url = "http://10.0.2.2:5000/api/tasks";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Making GET request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray tasksArray = new JSONArray(response);
                taskList.clear();  // Clear previous data

                // Loop through the JSON array to get each task and add it to taskList
                for (int i = 0; i < tasksArray.length(); i++) {
                    JSONObject taskObject = tasksArray.getJSONObject(i);

                    // Extract task details
                    String taskId = taskObject.getString("taskId");
                    String title = taskObject.getString("title");
                    String description = taskObject.getString("description");
                    String assignedTo = taskObject.getString("assignedTo");
                    String startDate = taskObject.getString("startDate");
                    String endDate = taskObject.getString("endDate");
                    String priority = taskObject.getString("priority");

                    // Create Task object and add it to taskList
                    Task task = new Task(taskId, title, description, assignedTo, startDate, endDate, priority);
                    taskList.add(task);
                }

                // Notify the adapter to refresh the ListView
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(Task2Activity.this, "Tasks fetched successfully", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Task2Activity.this, "Error parsing tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(Task2Activity.this, "Error fetching tasks: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        // Add the request to the queue
        requestQueue.add(stringRequest);
    }
}
