package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Week_2_Task_1 extends AppCompatActivity {

    EditText firstNumber, secondNumber;
    Button btnAdd, btnSub, btnMul, btnDiv;
    TextView result;
    String num1, num2;
    double first, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week2_task1);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        result = findViewById(R.id.txtResult);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addition();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtraction();
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplication();
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                division();
            }
        });

    }

    private boolean validateInputs() {
        num1 = firstNumber.getText().toString().trim();
        num2 = secondNumber.getText().toString().trim();
        result.setVisibility(View.VISIBLE);
        if (num1.isEmpty() || num2.isEmpty()) {
            result.setTextColor(getResources().getColor(R.color.red));
            result.setText("Please enter both numbers");
            return false;
        } else {
            try {
                first = Double.parseDouble(num1);
                second = Double.parseDouble(num2);
                result.setTextColor(getResources().getColor(R.color.white));
                return true;
            } catch (NumberFormatException e) {
                result.setTextColor(getResources().getColor(R.color.red));
                result.setText("Invalid input");
                return false;
            }
        }
    }

    private void division() {
        if (!validateInputs()) return;
        if (second == 0) {
            result.setTextColor(getResources().getColor(R.color.red));
            result.setText("Cannot divide by zero");
            return;
        }
        double div = first / second;
        result.setText(String.valueOf(div));
    }

    private void multiplication() {
        if (!validateInputs()) return;
        double mul = first * second;
        result.setText(String.valueOf(mul));
    }

    private void subtraction() {
        if (!validateInputs()) return;
        double sub = first - second;
        result.setText(String.valueOf(sub));
    }

    private void addition() {
        if (!validateInputs()) return;
        double sum = first + second;
        result.setText(String.valueOf(sum));
    }
}