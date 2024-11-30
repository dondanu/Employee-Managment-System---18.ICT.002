package com.example.emsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.List;

public class AttendanceListAdapter extends BaseAdapter {

    private Context context;
    private List<Attendance> attendanceList;

    public AttendanceListAdapter(Context context, List<Attendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @Override
    public int getCount() {
        return attendanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_attendance, parent, false);
        }

        Attendance attendance = attendanceList.get(position);
        TextView empID = convertView.findViewById(R.id.empID);
        TextView date = convertView.findViewById(R.id.date);
        TextView status = convertView.findViewById(R.id.status);

        empID.setText(attendance.getEmployeeId());
        date.setText(attendance.getDate());
        status.setText(attendance.getStatus());

        return convertView;
    }
}
