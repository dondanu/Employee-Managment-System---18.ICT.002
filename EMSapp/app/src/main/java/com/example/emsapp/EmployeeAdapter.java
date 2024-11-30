package com.example.emsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    // Context and Employee List variables
    private Context context;
    private List<Employee> employeeList;

    // Constructor (context மற்றும் employee list எடுக்கப்படுகிறது)
    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    // Layout Inflate செய்யும் method
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    // Data-வை Bind செய்யும் method
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.tvEmployeeID.setText("ID: " + employee.getEmployeeID());
        holder.tvName.setText("Name: " + employee.getName());
        holder.tvDesignation.setText("Designation: " + employee.getDesignation());
        holder.tvSalary.setText("Salary: " + employee.getSalary());
    }

    // Item count return செய்யும் method
    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    // ViewHolder class
    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmployeeID, tvName, tvDesignation, tvSalary;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeID = itemView.findViewById(R.id.tvEmployeeID);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesignation = itemView.findViewById(R.id.tvDesignation);
            tvSalary = itemView.findViewById(R.id.tvSalary);
        }
    }
}
