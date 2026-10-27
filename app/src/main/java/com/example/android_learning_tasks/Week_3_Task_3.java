package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Week_3_Task_3 extends AppCompatActivity {
    ScrollView view;
    TextInputLayout txtParagraph;
    TextInputEditText edtParagraph;
    Button btnAnalyze;
    TextView txtWord, txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week3_task3);

        view = findViewById(R.id.root);
        txtParagraph = findViewById(R.id.txtParagraph);
        edtParagraph = findViewById(R.id.edtParagraph);
        btnAnalyze = findViewById(R.id.btnAnalyze);
        txtWord = findViewById(R.id.txtWord);
        txtCount = findViewById(R.id.txtCount);

        btnAnalyze.setOnClickListener(v -> {
            String paragraph = edtParagraph.getText().toString().trim().toLowerCase();
            if(paragraph.isEmpty()){
                txtParagraph.setError("Paragraph is required");
                txtWord.setText("");
                txtCount.setText("");
                return;
            }
            txtParagraph.setError(null);
            getMostFrequentWord(paragraph);
        });
    }

    private void getMostFrequentWord(String paragraph) {
        hideKeyboard();
        String[] words = paragraph.split("[,.\\s]+");

        Map<String, Integer> wordCountMap = new HashMap<>();
        for (String word : words) {
            if(!word.isEmpty()){
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        int maxCount = 0;
        String maxWord = "";
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                maxWord = entry.getKey();
            }
            txtWord.setText(maxWord);
            txtCount.setText(String.valueOf(maxCount));
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
