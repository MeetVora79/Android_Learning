package com.example.android_learning_tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReacyclerProductAdapter extends RecyclerView.Adapter<ReacyclerProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductModel> productList;
    OnDataChangeListener listener;

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    public ReacyclerProductAdapter(Context context, ArrayList<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    public ReacyclerProductAdapter(Context context, ArrayList<ProductModel> productList, OnDataChangeListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cartitem_raw, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel currentItem = productList.get(position);
        holder.txtName.setText(currentItem.getName());
        holder.txtPrice.setText(String.valueOf(currentItem.getPrice()));
        holder.txtQuantValue.setText(String.valueOf(currentItem.getQuantity()));
        holder.txtSubTotalValue.setText(String.valueOf(currentItem.getSubTotal()));

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.cartupdate_raw);

                TextView edtProduct = dialog.findViewById(R.id.edtProduct);
                EditText edtPrice = dialog.findViewById(R.id.edtPrice);
                EditText edtQuantity = dialog.findViewById(R.id.edtQuantity);
                Button btnCartUpdate = dialog.findViewById(R.id.btnCartUpdate);

                edtProduct.setText(productList.get(pos).getName());
                edtPrice.setText(String.valueOf(productList.get(pos).getPrice()));
                edtQuantity.setText(String.valueOf(productList.get(pos).getQuantity()));

                btnCartUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String priceStr = edtPrice.getText().toString().trim();
                        String quantStr = edtQuantity.getText().toString().trim();
                        if (priceStr.isEmpty() || quantStr.isEmpty()) {
                            if (priceStr.isEmpty()) edtPrice.setError("Please enter product price");
                            if (quantStr.isEmpty()) edtQuantity.setError("Please enter product quantity");
                        } else {
                            double price = Double.parseDouble(priceStr);
                            int quant = Integer.parseInt(quantStr);
                            int currentPos = holder.getAdapterPosition();
                            if (currentPos != RecyclerView.NO_POSITION) {
                                productList.set(currentPos, new ProductModel(productList.get(currentPos).getName(), price, quant));
                                notifyItemChanged(currentPos);
                                if (listener != null) {
                                    listener.onDataChanged();
                                }
                            }
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Remove Product")
                        .setMessage("Are you sure to remove this product from cart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int pos = holder.getAdapterPosition();
                                if (pos != RecyclerView.NO_POSITION) {
                                    productList.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos, productList.size() - pos);
                                    if (listener != null) {
                                        listener.onDataChanged();
                                    }
                                    Toast.makeText(context, "Product Removed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQuantValue, txtSubTotalValue;
        LinearLayout llRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantValue = itemView.findViewById(R.id.txtQuantValue);
            txtSubTotalValue = itemView.findViewById(R.id.txtSubTotalValue);
            llRow = itemView.findViewById(R.id.llRow);
        }
    }
}
