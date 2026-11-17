package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsFavorite extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvFavEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.products_favorite);

        recyclerView = findViewById(R.id.rvFavProducts);
        tvFavEmpty = findViewById(R.id.tvFavEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductDatabase.getInstance(this).productDao().getFavProducts().observe(this, favList -> {
            if (favList == null || favList.isEmpty()) {
                tvFavEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvFavEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                ProductAdapter_W6_T1 adapter = new ProductAdapter_W6_T1(this, favList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}