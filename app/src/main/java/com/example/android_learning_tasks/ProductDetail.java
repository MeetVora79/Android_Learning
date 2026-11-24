package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductDetail extends AppCompatActivity {

    ImageView ivImage;
    TextView tvTitle, tvPrice, tvCategory, tvDescription, tvMessage;
    LinearLayout llMain;
    ImageButton ibFavDetail;
    int productId;
    ProductModel_w6_t1 currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product_detail);

        ivImage = findViewById(R.id.ivImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvCategory = findViewById(R.id.tvCategory);
        tvDescription = findViewById(R.id.tvDescription);
        tvMessage = findViewById(R.id.tvMessage);
        llMain = findViewById(R.id.llMain);
        ibFavDetail = findViewById(R.id.ibFavDetail);

        productId = getIntent().getIntExtra("id", -1);

        ibFavDetail.setOnClickListener(v -> {
            if (currentProduct == null) return;
            ProductDao dao = ProductDatabase.getInstance(this).productDao();
            ProductModel_w6_t1 existing = dao.getProductById(currentProduct.getId());
            if (existing == null) {
                dao.insert(currentProduct);
                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            } else {
                dao.delete(existing);
                Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
            updateFavIcon();
        });

        loadDetail();
    }

    private void loadDetail() {
        if (!Week_6_Task_2.isNetworkAvailable(this)) {
            showError("No internet connection");
            return;
        }
        RetrofitClient.getProductApi().getProductDetail(productId)
                .enqueue(new Callback<ProductModel_w6_t1>() {
                    @Override
                    public void onResponse(Call<ProductModel_w6_t1> call, Response<ProductModel_w6_t1> response) {
                        if (response.code() == 404) {
                            showError("Product not found");
                            return;
                        }
                        if (!response.isSuccessful() || response.body() == null) {
                            showError("Server error: " + response.code());
                            return;
                        }
                        showDetail(response.body());
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
    }

    private void showDetail(ProductModel_w6_t1 p) {
        currentProduct = p;
        updateFavIcon();
        llMain.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.GONE);
        tvTitle.setText(p.getTitle());
        tvPrice.setText("$" + p.getPrice());
        tvCategory.setText(p.getCategory());
        tvDescription.setText(p.getDescription());

        if (p.getImages() != null && !p.getImages().isEmpty()) {
            Glide.with(this)
                    .load(p.getImages().get(0))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivImage);
        }
    }

    private void updateFavIcon() {
        if (currentProduct == null) return;
        ProductModel_w6_t1 saved = ProductDatabase.getInstance(this).productDao().getProductById(currentProduct.getId());
        if (saved != null) {
            ibFavDetail.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            ibFavDetail.setImageResource(R.drawable.outline_favorite_24);
        }
    }

    private void showError(String msg) {
        tvMessage.setText(msg);
        tvMessage.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
    }
}
