package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class Week_4_Task_2 extends AppCompatActivity {

    TextInputLayout txtAmount, txtDiscount;
    TextInputEditText edtPrice, edtDiscount;
    Button btnReset, btnCalculate;
    TextView txtResult, tvError;
    LinearLayout llResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week4_task2);

        llResult = findViewById(R.id.llResult);
        txtAmount = findViewById(R.id.txtAmount);
        txtDiscount = findViewById(R.id.txtDiscount);
        edtPrice = findViewById(R.id.edtPrice);
        edtDiscount = findViewById(R.id.edtDiscount);
        btnReset = findViewById(R.id.btnReset);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);
        tvError = findViewById(R.id.tvError);

        btnCalculate.setOnClickListener(v -> {
            String priceStr = edtPrice.getText().toString().trim();
            String discountStr = edtDiscount.getText().toString().trim();
            if (priceStr.isEmpty()) {
                txtAmount.setError("Please enter amount");
                return;
            }
            if (discountStr.isEmpty()) {
                txtDiscount.setError("Please enter discount");
                return;
            }
            try {
                double price = Double.parseDouble(priceStr);
                double discount = Double.parseDouble(discountStr);
                if(price <= 0){
                    txtAmount.setError("Amount should be greater than 0");
                    return;
                }
                if(discount < 0 || discount > 100){
                    txtDiscount.setError("Discount should be between 0 and 100");
                    return;
                }
                txtAmount.setError(null);
                txtDiscount.setError(null);
                hideKeyboard();
                edtPrice.clearFocus();
                edtDiscount.clearFocus();
                double discountAmount = (price * discount) / 100;
                txtResult.setText(String.format(Locale.getDefault(), "%.2f", discountAmount));
                llResult.setVisibility(View.VISIBLE);
            } catch (NumberFormatException e) {
                tvError.setText("Invalid input: " + e.getMessage());
                tvError.setTextColor(getResources().getColor(R.color.red));
                tvError.setVisibility(View.VISIBLE);
            }
        });
        
        btnReset.setOnClickListener(v -> {
            edtPrice.setText("");
            edtDiscount.setText("");
            txtAmount.setError(null);
            txtDiscount.setError(null);
            llResult.setVisibility(View.GONE);
            txtResult.setText("");
            edtPrice.requestFocus();
            tvError.setVisibility(View.GONE);
            tvError.setText("");
        });
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
