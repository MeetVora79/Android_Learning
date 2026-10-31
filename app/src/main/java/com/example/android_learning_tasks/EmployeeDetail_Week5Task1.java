package com.example.android_learning_tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EmployeeDetail_Week5Task1 extends AppCompatActivity{

    TextView empId, empName, empRole, txtError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.employee_detail_w5_t1);

        empId = findViewById(R.id.empId);
        empName = findViewById(R.id.empName);
        empRole = findViewById(R.id.empRole);
        txtError = findViewById(R.id.txtError);

        Intent intent = getIntent();
        if(intent != null){
            int id = intent.getIntExtra("empId", 0);
            String name = intent.getStringExtra("empName");
            String role = intent.getStringExtra("empRole");

            if(id != 0 && name != null && role != null){
                empId.setText(String.valueOf(id));
                empName.setText(name);
                empRole.setText(role);
            }else{
                txtError.setVisibility(View.VISIBLE);
            }
        }else{
            txtError.setVisibility(View.VISIBLE);
        }
    }
}
