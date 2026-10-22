package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Week_2_Task_3 extends AppCompatActivity {
    EditText edtText;
    Button btnAnalyze;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week2_task3);

        edtText = findViewById(R.id.edtText);
        btnAnalyze = findViewById(R.id.btnAnalyze);
        txtResult = findViewById(R.id.txtResult);

        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edtText.getText().toString().trim();
                if (text.isEmpty()) {
                    txtResult.setTextColor(getResources().getColor(R.color.red));
                    txtResult.setText("Please enter text");
                    txtResult.setVisibility(View.VISIBLE);
                    return;
                }
                int charCount = text.length();
                String[] words = text.split("\\s+");
                int wordCount = words.length;

                int vowelCount = 0;
                int spaceCount = 0;
                String lowerText = text.toLowerCase();
                for(int i = 0; i<lowerText.length(); i++){
                    char ch = lowerText.charAt(i);
                    if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
                        vowelCount++;
                    } else if (ch == ' ') {
                        spaceCount++;
                    }
                }

                txtResult.setVisibility(View.VISIBLE);
                txtResult.setText("Character Count: " + charCount + "\nWord Count: " + wordCount + "\nVowel Count: " + vowelCount + "\nSpace Count: " + spaceCount);
            }
        });
    }
}
