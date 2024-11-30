package com.example.emsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Task> taskList;

    public TaskAdapter(Context context, ArrayList<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            // Inflate the task item layout
            view = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
        } else {
            view = convertView;
        }

        // Get the task at the current position
        Task task = taskList.get(position);

        // Bind task data to the views
        TextView taskIdTextView = view.findViewById(R.id.task_id);
        TextView titleTextView = view.findViewById(R.id.task_title);
        TextView descriptionTextView = view.findViewById(R.id.task_description);
        TextView priorityTextView = view.findViewById(R.id.task_priority);
        Button deleteButton = view.findViewById(R.id.btn_delete);

        taskIdTextView.setText(task.getTaskId());
        titleTextView.setText(task.getTitle());
        descriptionTextView.setText(task.getDescription());
        priorityTextView.setText(task.getPriority());

        // Set up delete button to remove task from taskList and MongoDB
        deleteButton.setOnClickListener(v -> {
            // Remove the task from MongoDB and local list
            deleteTaskFromDatabase(task.getTaskId(), position);
        });

        return view;
    }

    private void deleteTaskFromDatabase(String taskId, int position) {
        String url = "http://10.0.2.2:5000/api/tasks/" + taskId; // Your backend DELETE route

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Create the DELETE request
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    // On success, remove the task from the local list
                    taskList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Task Deleted Successfully: " + taskId, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // On failure, show an error message
                    Toast.makeText(context, "Failed to Delete Task: " + taskId, Toast.LENGTH_SHORT).show();
                });

        // Add the request to the request queue
        requestQueue.add(deleteRequest);
    }
}
