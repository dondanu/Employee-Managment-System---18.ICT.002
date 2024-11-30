package com.example.emsapp;

// LeaveAdapter.java

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class LeaveAdapter extends ArrayAdapter<Leave> {

    private Activity context;
    private List<Leave> leaveList;

    public LeaveAdapter(Activity context, List<Leave> leaveList) {
        super(context, R.layout.leave_item, leaveList);
        this.context = context;
        this.leaveList = leaveList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.leave_item, null, true);

        TextView txtEmployeeId = listViewItem.findViewById(R.id.txtEmployeeId);
        TextView txtLeaveType = listViewItem.findViewById(R.id.txtLeaveType);
        TextView txtStartDate = listViewItem.findViewById(R.id.txtStartDate);
        TextView txtEndDate = listViewItem.findViewById(R.id.txtEndDate);
        Button btnDelete = listViewItem.findViewById(R.id.btnDelete);

        Leave leave = leaveList.get(position);
        txtEmployeeId.setText("Employee ID: " + leave.getEmployeeId());
        txtLeaveType.setText("Leave Type: " + leave.getLeaveType());
        txtStartDate.setText("Start Date: " + leave.getStartDate());
        txtEndDate.setText("End Date: " + leave.getEndDate());

        // Delete leave when clicked
        btnDelete.setOnClickListener(v -> {
            // Delete leave using employeeId and handle response
            deleteLeave(leave.getEmployeeId(), position); // Pass employeeId, not leaveId
        });

        return listViewItem;
    }

    // Improved deleteLeave method
    private void deleteLeave(String employeeId, int position) {
        String url = "http://10.0.2.2:5000/api/leaves/" + employeeId;  // Pass employeeId here

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    // Handle successful deletion
                    Toast.makeText(context, "Leave deleted successfully", Toast.LENGTH_SHORT).show();

                    // Remove the deleted leave from the list and update the UI
                    leaveList.remove(position);  // Remove the leave at the clicked position
                    notifyDataSetChanged(); // Notify adapter to update ListView
                },
                error -> {
                    // Handle error
                    error.printStackTrace(); // Log the error for debugging
                    Toast.makeText(context, "Error deleting leave", Toast.LENGTH_SHORT).show();
                });

        // Make sure you have the request queue initialized
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
