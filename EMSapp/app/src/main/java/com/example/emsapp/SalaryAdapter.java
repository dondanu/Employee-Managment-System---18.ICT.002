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
import com.example.emsapp.Salary;

import java.util.ArrayList;

public class SalaryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Salary> salaryList;

    public SalaryAdapter(Context context, ArrayList<Salary> salaryList) {
        this.context = context;
        this.salaryList = salaryList;
    }

    @Override
    public int getCount() {
        return salaryList.size();
    }

    @Override
    public Object getItem(int position) {
        return salaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.salary_list_item, parent, false);
        }

        TextView txtEmployeeId = convertView.findViewById(R.id.txt_employee_id);
        TextView txtBaseSalary = convertView.findViewById(R.id.txt_base_salary);
        Button btnDelete = convertView.findViewById(R.id.btn_delete);

        Salary salary = salaryList.get(position);
        txtEmployeeId.setText("Employee ID: " + salary.getEmployeeId());
        txtBaseSalary.setText("Base Salary: " + salary.getBaseSalary());

        btnDelete.setOnClickListener(v -> deleteSalary(salary.getEmployeeId(), position));

        return convertView;
    }

    private void deleteSalary(String employeeId, int position) {
        String url = "http://10.0.2.2:5000/api/salary/" + employeeId;

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    // On successful deletion, remove the item from the list and notify the adapter
                    salaryList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Salary deleted successfully", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error response
                    Toast.makeText(context, "Error deleting salary", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
