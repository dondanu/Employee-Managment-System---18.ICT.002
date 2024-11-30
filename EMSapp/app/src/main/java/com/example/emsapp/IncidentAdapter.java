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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class IncidentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SafetyIncident> incidents;

    public IncidentAdapter(Context context, ArrayList<SafetyIncident> incidents) {
        this.context = context;
        this.incidents = incidents;
    }

    @Override
    public int getCount() {
        return incidents.size();
    }

    @Override
    public Object getItem(int position) {
        return incidents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recycle the view or inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.incident_list_item, parent, false);
        }

        // Initialize views
        TextView incidentIdText = convertView.findViewById(R.id.incident_id);
        TextView descriptionText = convertView.findViewById(R.id.description);
        Button btnDelete = convertView.findViewById(R.id.btn_delete);

        // Get current incident
        SafetyIncident incident = incidents.get(position);

        // Set values for the views
        incidentIdText.setText(incident.getIncidentId());
        descriptionText.setText(incident.getDescription());

        // Delete button click handler
        btnDelete.setOnClickListener(v -> {
            // Perform DELETE request to the backend API
            String url = "http://10.0.2.2:5000/api/safety/" + incident.getIncidentId();

            StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                    response -> {
                        // Successfully deleted
                        incidents.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        // Handle error
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            // Add the request to the Volley queue
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(deleteRequest);
        });

        return convertView;
    }
}
