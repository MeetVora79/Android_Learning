package com.example.android_learning_tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter_W6_T1 extends RecyclerView.Adapter<ProductAdapter_W6_T1.ViewHolder> {
    Context context;
    List<ProductModel_w6_t1> products;
    public ProductAdapter_W6_T1(Context context, List<ProductModel_w6_t1> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter_W6_T1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_raw_w6_t1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter_W6_T1.ViewHolder holder, int position) {
        ProductModel_w6_t1 product = products.get(position);
        holder.ivTitle.setText(product.getTitle());
        holder.ivPrice.setText(String.valueOf(product.getPrice()));
        Glide.with(holder.itemView.getContext())
                .load(product.getThumbnail())
                .into(holder.ivProducts);

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            Intent intent = new Intent(context, ProductDetail.class);
            intent.putExtra("id", products.get(pos).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProducts;
        TextView ivTitle, ivPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProducts = itemView.findViewById(R.id.ivProducts);
            ivTitle = itemView.findViewById(R.id.tvTitle);
            ivPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
