package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class Week_2_Task_2 extends AppCompatActivity {

    EditText edtSdtCount, edtMarks;
    Button btnSummary;
    TextView txtResult;
    int[] marks;
    int total, average, highest, lowest, goodAvgCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week2_task2);

        edtSdtCount = findViewById(R.id.edtSdtCount);
        edtMarks = findViewById(R.id.edtMarks);
        btnSummary = findViewById(R.id.btnSummary);
        txtResult = findViewById(R.id.txtResult);

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countStr = edtSdtCount.getText().toString().trim();
                String marksStr = edtMarks.getText().toString().trim();
                if (countStr.isEmpty() || marksStr.isEmpty()) {
                    setResultMessage("Please enter both fields", true);
                    return;
                }

                int studentCount;
                try {
                    studentCount = Integer.parseInt(countStr);
                } catch (NumberFormatException e) {
                    setResultMessage("Please enter a valid numeric student count", true);
                    return;
                }

                if (studentCount <= 0) {
                    setResultMessage("Student count cannot be negative or zero", true);
                    return;
                }

                String[] rawMarks = marksStr.split(",");

                if (rawMarks.length != studentCount) {
                    setResultMessage("Marks count (" + rawMarks.length + ") does not match student count (" + studentCount + ")", true);
                    return;
                }

                marks = new int[studentCount];
                for (int i = 0; i < rawMarks.length; i++) {
                    String part = rawMarks[i].trim();
                    if (part.isEmpty()) {
                        setResultMessage("Invalid marks format or Empty value at index " + i + "\n", true);
                        return;
                    }
                    try {
                        int mark = Integer.parseInt(part);
                        if (mark < 0 || mark > 100) {
                            setResultMessage("Marks out of range (0-100)", true);
                            return;
                        }
                        marks[i] = mark;
                    } catch (NumberFormatException e) {
                        setResultMessage("Non-numeric marks entered: '" + part + "'", true);
                        return;
                    }
                }
                generateSummary();
            }
        });
    }

    private void generateSummary() {
        total = 0;
        goodAvgCount = 0;

        // Total marks
        for (int mark : marks) {
            total += mark;
        }

        // Average marks
        average = total / marks.length;

        Arrays.sort(marks);
        // Highest and lowest marks
        highest = marks[marks.length - 1];
        lowest = marks[0];

        // students with marks greater than average
        for (int mark : marks) {
            if (mark > average) {
                goodAvgCount++;
            }
        }
        setResultMessage("Total: " + total + "\nAverage: " + average + "\nHighest: " + highest + "\nLowest: " + lowest + "\n\nStudents (Marks > Average): " + goodAvgCount, false);
    }

    private void setResultMessage(String message, Boolean isError){
        if(isError){
            txtResult.setTextColor(getResources().getColor(R.color.red));
        }else{
            txtResult.setTextColor(getResources().getColor(R.color.white));
        }
        txtResult.setText(message);
        txtResult.setVisibility(View.VISIBLE);
    }
}
