package com.example.android_learning_tasks;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Set;

public class Week_3_Task_2 extends AppCompatActivity {
    TextInputLayout txtEmail;
    TextInputEditText edtEmail;
    Button btnRegister;
    ScrollView view;
    TextView txtUnique, txtDuplicate;
    int uniqueCount = 0;
    int duplicateCount = 0;
    String input;
    Set<String> inputSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week3_task2);

        txtEmail = findViewById(R.id.txtEmail);
        edtEmail = findViewById(R.id.edtEmail);
        btnRegister = findViewById(R.id.btnRegister);
        view = findViewById(R.id.root);
        txtUnique = findViewById(R.id.txtUnique);
        txtDuplicate = findViewById(R.id.txtDuplicate);

        btnRegister.setOnClickListener(v -> {
            attemptRegister();
        });
        edtEmail.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                attemptRegister();
                return true;
            }
            return false;
        });
    }

    private void attemptRegister() {
        hideKeyboard();
        input = edtEmail.getText().toString().trim().toLowerCase();
        boolean isValidate = validateEmail(input);

        if(isValidate){
            boolean isAdded = inputSet.add(input);
            if(isAdded){
                uniqueCount++;
                edtEmail.setText("");
                Snackbar.make(view, "Email or username added successfully", Snackbar.LENGTH_SHORT).show();
            }else{
                duplicateCount++;
                Snackbar.make(view, "Email or username already exists", Snackbar.LENGTH_SHORT).show();
            }
            txtUnique.setText("Unique: " + String.valueOf(uniqueCount));
            txtDuplicate.setText("Duplicate: "  + String.valueOf(duplicateCount));
        }
    }

    private boolean validateEmail(String input) {
        if(input.isEmpty()){
            txtEmail.setError("Email or username is required");
            return false;
        }
        if(input.contains("@")){
            if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
                txtEmail.setError("Email is invalid");
                return false;
            }
        }else{
            if(!input.matches("[a-z0-9_.]{3,20}")){
                txtEmail.setError("Invalid Username: 3-20 chars, only letters, numbers, _ or .");
                return false;
            }
        }
        txtEmail.setError(null);
        return true;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
