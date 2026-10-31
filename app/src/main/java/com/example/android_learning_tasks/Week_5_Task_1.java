package com.example.android_learning_tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Week_5_Task_1 extends AppCompatActivity {

    Button btnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week5_task1);

        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(v -> {
            startActivity(new Intent(this, EmployeeList_Week5Task1.class));
        });
    }
}
