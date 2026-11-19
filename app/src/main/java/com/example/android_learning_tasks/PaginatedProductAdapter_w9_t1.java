package com.example.android_learning_tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PaginatedProductAdapter_w9_t1 extends RecyclerView.Adapter<PaginatedProductAdapter_w9_t1.ViewHolder> {

    Context context;
    List<ProductModel_w6_t1> products;

    public PaginatedProductAdapter_w9_t1(Context context, List<ProductModel_w6_t1> products){
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public PaginatedProductAdapter_w9_t1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.paginated_product_item_raw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaginatedProductAdapter_w9_t1.ViewHolder holder, int position) {
        ProductModel_w6_t1 product = products.get(position);
        holder.tvTitle.setText(product.getTitle());
        holder.tvDescription.setText(product.getDescription());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
