package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.TreeSet;

public class Week_2_Task_4 extends AppCompatActivity {

    EditText edtArr, edtTarget, edtReverse;
    Button btnStart;
    TextView txtResult;
    StringBuilder result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week2_task4);

        result = new StringBuilder();

        edtArr = findViewById(R.id.edtArr);
        edtTarget = findViewById(R.id.edtTarget);
        btnStart = findViewById(R.id.btnStart);
        edtReverse = findViewById(R.id.edtReverse);
        txtResult = findViewById(R.id.txtResult);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setLength(0);

                String arrText = edtArr.getText().toString().trim();
                String targetText = edtTarget.getText().toString().trim();
                String reverse = edtReverse.getText().toString().trim();

                if(arrText.isEmpty() || targetText.isEmpty() || reverse.isEmpty()){
                    setResultMessage("Please enter all fields\n", true);
                    return;
                }

                int[] arr;
                int target;
                try {
                    String[] parts = arrText.split("\\s*,\\s*");
                    arr = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        arr[i] = Integer.parseInt(parts[i]);
                    }
                    target = Integer.parseInt(targetText);
                } catch (NumberFormatException e) {
                    setResultMessage("Invalid inputs in array\n", true);
                    return;
                }

                searchElement(arr,target); // search targetText value from given array
                secondLarge(arr); // find second-largest element from given array
                reverseString(reverse); // reverse a given string
            }
        });
    }

    private void reverseString(String reverse) {
        StringBuilder sb = new StringBuilder(reverse);
        sb.reverse();
        setResultMessage("Reversed String: " + sb + "\n", false);
    }

    private void secondLarge(int[] arr) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int x : arr) set.add(x);

        if (set.size() < 2) {
            setResultMessage("No second largest element\n", true);
        } else {
            setResultMessage("Second largest element: " + set.lower(set.last()) + "\n", false);
        }
    }

    private void searchElement(int[] arr, int target) {
        for(int i = 0; i<arr.length; i++){
            if(arr[i] == target){
                setResultMessage("Element found at index: " + i + "\n", false);
                return;
            }
        }
        setResultMessage("Element not found: -1\n", true);
    }

    private void setResultMessage(String message, Boolean isError){
        if(isError){
            txtResult.setTextColor(getResources().getColor(R.color.red));
        }else{
            txtResult.setTextColor(getResources().getColor(R.color.white));
        }
        result.append(message);
        txtResult.setText(result);
        txtResult.setVisibility(View.VISIBLE);
    }
}
