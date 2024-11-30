package com.example.emsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UpdateEmployeeAdapter extends RecyclerView.Adapter<UpdateEmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private List<Employee> employeeList;

    public UpdateEmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee_update, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.tvEmployeeID.setText("ID: " + employee.getEmployeeID());
        holder.tvName.setText("Name: " + employee.getName());
        holder.tvDesignation.setText("Designation: " + employee.getDesignation());
        holder.btnUpdate.setOnClickListener(view -> {
            // When the update button is clicked, go to Update2Activity
            Intent intent = new Intent(context, Update2Activity.class);
            intent.putExtra("employeeID", employee.getEmployeeID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmployeeID, tvName, tvDesignation;
        Button btnUpdate;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeID = itemView.findViewById(R.id.tvEmployeeID);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesignation = itemView.findViewById(R.id.tvDesignation);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
