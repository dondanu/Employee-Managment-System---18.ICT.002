package com.example.emsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class AttendanceAdapter extends ArrayAdapter<Attendance> {
    private Activity context;
    private List<Attendance> attendanceList;

    public AttendanceAdapter(Activity context, List<Attendance> attendanceList) {
        super(context, R.layout.attendance_list_item, attendanceList);
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.attendance_list_item, null);
        }

        Attendance attendance = attendanceList.get(position);
        TextView txtEmployeeId = view.findViewById(R.id.txtEmployeeId);
        TextView txtDate = view.findViewById(R.id.txtDate);
        TextView txtStatus = view.findViewById(R.id.txtStatus);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        // Bind the attendance details
        txtEmployeeId.setText(attendance.getEmployeeId());
        txtDate.setText(attendance.getDate());
        txtStatus.setText(attendance.getStatus());

        // Delete button functionality
        btnDelete.setOnClickListener(v -> {
            String attendanceId = attendance.getId(); // Ensure this fetches the _id
            if (attendanceId == null || attendanceId.isEmpty()) {
                Toast.makeText(context, "Invalid ID", Toast.LENGTH_SHORT).show();
            } else {
                deleteAttendance(attendanceId, position);
            }
        });


        return view;
    }

    private void deleteAttendance(String id, int position) {
        String url = "http://10.0.2.2:5000/api/attendance/" + id; // Use the `_id`

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    attendanceList.remove(position); // Remove from the list
                    notifyDataSetChanged(); // Refresh the adapter
                },
                error -> {
                    error.printStackTrace(); // Log the error for debugging
                    Toast.makeText(context, "Error Deleting Attendance", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
