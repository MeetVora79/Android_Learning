package com.example.android_learning_tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Week_6_Task_3 extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar pbLoading;
    TextView tvMessage;
    List<ProductModel_w6_t1> products;
    ProductAdapter_W6_T1 adapter;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week6_task3);

        pbLoading = findViewById(R.id.pbLoading);
        tvMessage = findViewById(R.id.tvMessage);
        recyclerView = findViewById(R.id.rvProducts);
        etSearch = findViewById(R.id.etSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                searchProduct(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void searchProduct(String s) {
        RetrofitClient.getProductApi().searchProducts(s).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    showError("Server error: " + response.code());
                    return;
                }
                products = response.body().getProducts();
                if(products == null || products.isEmpty()){
                    showError("No products found");
                    return;
                }
                showProducts(products);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
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

    private void loadProducts(){
        showLoading();
        if(!isNetworkAvailable(this)){
            showError("No internet connection");
            return;
        }
        RetrofitClient.getProductApi().getProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    showError("Server error: " + response.code());
                    return;
                }
                if(response.code() == 404){
                    showError("Page Not Found " + response.code());
                    return;
                }
                if(response.code() == 500){
                    showError("Server error " + response.code());
                    return;
                }
                products = response.body().getProducts();
                if(products == null || products.isEmpty()){
                    showError("No products found");
                    return;
                }
                showProducts(products);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
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

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showProducts(List<ProductModel_w6_t1> products) {
        pbLoading.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new ProductAdapter_W6_T1(this, products);
        recyclerView.setAdapter(adapter);
    }

    private void showError(String msg) {
        pbLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tvMessage.setText(msg);
        tvMessage.setVisibility(View.VISIBLE);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
