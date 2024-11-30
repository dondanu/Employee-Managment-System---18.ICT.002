package com.example.emsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Welfare2Activity extends AppCompatActivity {

    TextView tvBenefits;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare2);

        tvBenefits = findViewById(R.id.tv_benefits);
        btnBack = findViewById(R.id.btn_back);

        fetchBenefits();

        btnBack.setOnClickListener(view -> finish());
    }

    private void fetchBenefits() {
        String url = "http://10.0.2.2:5000/api/benefits";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject benefit = response.getJSONObject(i);
                            builder.append("Employee ID: ").append(benefit.getString("employeeId"))
                                    .append("\nBenefit: ").append(benefit.getString("benefitType"))
                                    .append("\nStart Date: ").append(benefit.getString("startDate"))
                                    .append("\nEnd Date: ").append(benefit.getString("endDate"))
                                    .append("\n\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    tvBenefits.setText(builder.toString());
                },
                error -> Toast.makeText(Welfare2Activity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());

        requestQueue.add(request);
    }
}
