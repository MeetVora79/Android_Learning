package com.example.android_learning_tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Week_6_Task_1 extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar pbLoading;
    TextView tvMessage;
    Button btnRetry;
    List<ProductModel_w6_t1> products;
    ProductAdapter_W6_T1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week6_task1);

        pbLoading = findViewById(R.id.pbLoading);
        tvMessage = findViewById(R.id.tvMessage);
        btnRetry = findViewById(R.id.btnRetry);
        recyclerView = findViewById(R.id.rvProducts);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnRetry.setOnClickListener(v -> {
            loadProducts();
        });
        loadProducts();
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
                Log.d("ProductResponse", products.toString());
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
        btnRetry.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showProducts(List<ProductModel_w6_t1> products) {
        pbLoading.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new ProductAdapter_W6_T1(this, products);
        recyclerView.setAdapter(adapter);
    }

    private void showError(String msg) {
        pbLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tvMessage.setText(msg);
        tvMessage.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
