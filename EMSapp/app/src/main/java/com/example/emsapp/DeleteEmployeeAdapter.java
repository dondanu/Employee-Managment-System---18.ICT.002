package com.example.emsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DeleteEmployeeAdapter extends RecyclerView.Adapter<DeleteEmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private List<Employee> employeeList;
    private OnDeleteClickListener deleteClickListener;

    public DeleteEmployeeAdapter(Context context, List<Employee> employeeList, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.employeeList = employeeList;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee_delete, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.tvEmployeeID.setText("ID: " + employee.getEmployeeID());
        holder.tvName.setText("Name: " + employee.getName());
        holder.btnDelete.setOnClickListener(view -> deleteClickListener.onDeleteClick(employee));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmployeeID, tvName;
        Button btnDelete;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeID = itemView.findViewById(R.id.tvEmployeeID);
            tvName = itemView.findViewById(R.id.tvName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Employee employee);
    }
}
