package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class Week_9_Task_2 extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar pbLoading;
    Spinner spCategory, spSort;
    Button btnClear;
    List<ProductModel_w6_t1> products = new ArrayList<>();
    PaginatedProductAdapter_w9_t1 adapter;
    List<String> categoryList = new ArrayList<>();
    String selectedCategory = null;
    int selectedCategoryIndex = 0;
    String sortBy = null;
    String order = null;
    int selectedSortIndex = 0;
    Call<ProductResponse> currentCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.week9_task2);

        recyclerView = findViewById(R.id.rvProducts);
        pbLoading = findViewById(R.id.pbLoading);
        spCategory = findViewById(R.id.spCategory);
        spSort = findViewById(R.id.spSort);
        btnClear = findViewById(R.id.btnClear);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaginatedProductAdapter_w9_t1(this, products);
        recyclerView.setAdapter(adapter);

        bindCategoryList(); // category spinner ka data API call se

        setupCategorySpinner(); // category select pe filter API call
        setupSortSpinner(); // sort data with price

        btnClear.setOnClickListener(v -> clearFilters());
        loadProducts(); // load all data
    }

    private void clearFilters() {
        selectedCategory = null;
        selectedCategoryIndex = 0;
        sortBy = null;
        order = null;
        selectedSortIndex = 0;

        spCategory.setSelection(0);
        spSort.setSelection(0);

        loadProducts();
    }

    private void bindCategoryList() {
        RetrofitClient.getProductApi().getCategoryList().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    showError("Categories not loaded: " + response.code());
                    return;
                }
                categoryList.clear();
                categoryList.addAll(response.body());

                List<String> labels = new ArrayList<>();
                labels.add("All categories");
                for (String slug : categoryList) {
                    String s = slug.replace("-", " ");
                    labels.add(Character.toUpperCase(s.charAt(0)) + s.substring(1));
                }

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(Week_9_Task_2.this, android.R.layout.simple_spinner_item, labels);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                showError("Categories not loaded");
            }
        });
    }

    private void setupCategorySpinner() {
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == selectedCategoryIndex) return;
                selectedCategoryIndex = position;
                selectedCategory = (position == 0) ? null : categoryList.get(position-1);
                loadProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSortSpinner() {
        String[] options = {"Default", "Price: Low to High", "Price: High to Low"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, options);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSort.setAdapter(sortAdapter);

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == selectedSortIndex) return;
                selectedSortIndex = position;

                if(position == 1){ sortBy = "price"; order = "asc"; }
                else if(position == 2) { sortBy = "price"; order = "desc"; }
                else { sortBy = null; order = null; }

                loadProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadProducts() {
        if (currentCall != null) currentCall.cancel();
        pbLoading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        products.clear();
        adapter.notifyDataSetChanged();

        Call<ProductResponse> call = (selectedCategory == null)
                ? RetrofitClient.getProductApi().getProducts(sortBy, order)
                : RetrofitClient.getProductApi().getProductsByCategory(selectedCategory, sortBy, order);
        currentCall = call;

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                pbLoading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (!response.isSuccessful() || response.body() == null) {
                    showError("Server error: " + response.code());
                    return;
                }
                List<ProductModel_w6_t1> newProducts = response.body().getProducts();
                products.addAll(newProducts);
                adapter.notifyDataSetChanged();

                if (products.isEmpty()) showError("No products found");
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                if (call.isCanceled()) return;
                pbLoading.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentCall != null) currentCall.cancel();
    }
}
