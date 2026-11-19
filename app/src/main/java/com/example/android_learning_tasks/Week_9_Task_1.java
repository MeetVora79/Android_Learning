package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Week_9_Task_1 extends AppCompatActivity {

    static final int LIMIT = 10;
    int SKIP = 0;
    RecyclerView recyclerView;
    Button btnLoad;
    ProgressBar pbLoading;
    List<ProductModel_w6_t1> products = new ArrayList<>();
    PaginatedProductAdapter_w9_t1 adapter;
    boolean isLoading = false;
    int totalProducts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.week9_task1);

        recyclerView = findViewById(R.id.rvProducts);
        btnLoad = findViewById(R.id.btnLoad);
        pbLoading = findViewById(R.id.pbLoading);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaginatedProductAdapter_w9_t1(this, products);
        recyclerView.setAdapter(adapter);

        loadProducts();

        btnLoad.setOnClickListener(v -> {
            if (!isLoading) {
                if (products.size() >= totalProducts && totalProducts > 0) {
                    Toast.makeText(this, "No more products available", Toast.LENGTH_SHORT).show();
                    btnLoad.setEnabled(false);
                    return;
                }
                SKIP += LIMIT;
                loadProducts();
            }
        });
    }

    private void loadProducts() {
        if (isLoading) return;
        isLoading = true;
        pbLoading.setVisibility(View.VISIBLE);
        btnLoad.setEnabled(false);

        RetrofitClient.getProductApi().getPaginatedProducts(LIMIT, SKIP).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                isLoading = false;
                pbLoading.setVisibility(View.GONE);
                btnLoad.setEnabled(true);

                if (!response.isSuccessful() || response.body() == null) {
                    showError("Server error: " + response.code());
                    return;
                }

                ProductResponse productResponse = response.body();
                totalProducts = productResponse.getTotal();
                List<ProductModel_w6_t1> newProducts = productResponse.getProducts();

                if (newProducts != null && !newProducts.isEmpty()) {
                    int startPosition = products.size();
                    products.addAll(newProducts);
                    adapter.notifyItemRangeInserted(startPosition, newProducts.size());
                }

                if (products.size() >= totalProducts && totalProducts > 0) {
                    btnLoad.setEnabled(false);
                    btnLoad.setText("End of list");
                    Toast.makeText(Week_9_Task_1.this, "All products loaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                isLoading = false;
                pbLoading.setVisibility(View.GONE);
                btnLoad.setEnabled(true);
                if (t instanceof SocketTimeoutException) {
                    showError("Request timed out");
                } else if (t instanceof IOException) {
                    showError("Network problem. Check connection");
                } else {
                    showError("Something went wrong: " + t.getMessage());
                }
            }
        });
    }

    private void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
