package com.example.android_learning_tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Week_6_Task_4 extends AppCompatActivity {

    TextInputLayout tilTitle, tilPrice, tilCate;
    TextInputEditText etTitle, etPrice, etCate;
    TextView tvResult;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.week6_task4);

        tilTitle = findViewById(R.id.tilTitle);
        tilPrice = findViewById(R.id.tilPrice);
        tilCate = findViewById(R.id.tilCate);
        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etCate = findViewById(R.id.etCate);
        tvResult = findViewById(R.id.tvResult);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String category = etCate.getText().toString().trim();
            if(title.isEmpty()){
                tilTitle.setError("Title is required");
                return;
            }
            if(priceStr.isEmpty()){
                tilPrice.setError("Price is required");
                return;
            }
            if(category.isEmpty()){
                tilCate.setError("Category is required");
                return;
            }
            tilTitle.setError("");
            tilPrice.setError("");
            tilCate.setError("");
            double price = Double.parseDouble(priceStr);
            ProductModel_w6_t1 product = new ProductModel_w6_t1(title, price, category);
            RetrofitClient.getProductApi().addProduct(product).enqueue(new Callback<ProductModel_w6_t1>() {
                @Override
                public void onResponse(Call<ProductModel_w6_t1> call, Response<ProductModel_w6_t1> response) {
                    if(!response.isSuccessful() || response.body() == null){
                        showError("Server error: " + response.code());
                        return;
                    }
                    ProductModel_w6_t1 p = response.body();
                    Log.d("ResponseProduct", "Title: "+ p.getTitle() + "\nPrice: "+ p.getPrice() + "\nCategory: "+ p.getCategory());
                    tvResult.setVisibility(View.VISIBLE);
                    tvResult.setText("Title: "+ p.getTitle() + "\nPrice: "+ p.getPrice() + "\nCategory: "+ p.getCategory());
                }

                @Override
                public void onFailure(Call<ProductModel_w6_t1> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        showError("Request timed out");
                    } else if (t instanceof IOException) {
                        showError("Network problem. Check connection");
                    } else {
                        showError("Something went wrong: " + t.getMessage());
                    }
                }
            });
        });
    }

    private void showError(String s) {
        tvResult.setText(s);
//        tvResult.setTextColor();
    }
}