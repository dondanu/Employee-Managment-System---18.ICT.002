package com.example.emsapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WelfareActivity extends AppCompatActivity {
    EditText etEmployeeId, etBenefitType, etStartDate, etEndDate;
    Button btnAddBenefit, btnViewBenefits, btnBackToHome;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);

        etEmployeeId = findViewById(R.id.et_employee_id);
        etBenefitType = findViewById(R.id.et_benefit_type);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);

        btnAddBenefit = findViewById(R.id.btn_add_benefit);
        btnViewBenefits = findViewById(R.id.btn_view_benefits);
        btnBackToHome = findViewById(R.id.btn_back_to_home);

        requestQueue = Volley.newRequestQueue(this);

        btnAddBenefit.setOnClickListener(view -> addBenefit());
        btnViewBenefits.setOnClickListener(view -> {
            Intent intent = new Intent(WelfareActivity.this, Welfare2Activity.class);
            startActivity(intent);
        });
        btnBackToHome.setOnClickListener(view -> finish());
    }

    private void addBenefit() {
        String employeeId = etEmployeeId.getText().toString();
        String benefitType = etBenefitType.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();

        JSONObject benefitData = new JSONObject();
        try {
            benefitData.put("employeeId", employeeId);
            benefitData.put("benefitType", benefitType);
            benefitData.put("startDate", startDate);
            benefitData.put("endDate", endDate);

            String url = "http://10.0.2.2:5000/api/benefits";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, benefitData,
                    response -> Toast.makeText(WelfareActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show(),
                    error -> Toast.makeText(WelfareActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());

            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
