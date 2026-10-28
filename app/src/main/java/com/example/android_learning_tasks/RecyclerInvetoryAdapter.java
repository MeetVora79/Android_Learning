package com.example.android_learning_tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RecyclerInvetoryAdapter extends RecyclerView.Adapter<RecyclerInvetoryAdapter.ViewHolder> {

    Context context;
    LinkedHashMap<Integer, InventoryProductData> inventoryMap;
    ArrayList<Integer> idList;
    RecyclerInvetoryAdapter.OnDataChangeListener listener;

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    public RecyclerInvetoryAdapter(Context context,
                                   LinkedHashMap<Integer, InventoryProductData> inventoryMap,
                                   OnDataChangeListener listener) {
        this.context = context;
        this.inventoryMap = inventoryMap;
        this.idList = new ArrayList<>(inventoryMap.keySet());
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerInvetoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.inventory_raw, parent, false);
        RecyclerInvetoryAdapter.ViewHolder vh = new RecyclerInvetoryAdapter.ViewHolder(v);
        return vh;
    }

    public int getPositionForId(int productId) {
        return idList.indexOf(productId); // Item ka exact position index return karega
    }

    public boolean addProduct(int id, InventoryProductData data) {
        if (inventoryMap.containsKey(id)) return false;
        inventoryMap.put(id, data);
        idList.add(id);
        notifyItemInserted(idList.size() - 1);
        if (listener != null) listener.onDataChanged();
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerInvetoryAdapter.ViewHolder holder, int position) {
        int id = idList.get(position);
        InventoryProductData data = inventoryMap.get(id);

        holder.tvProductId.setText(String.valueOf(id));
        holder.tvPrice.setText(String.valueOf(data.getPrice()));
        holder.tvStock.setText(String.valueOf(data.getStock()));

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                int productId = idList.get(pos);
                InventoryProductData current = inventoryMap.get(productId);

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_invetory_product);

                TextView txtTitle = dialog.findViewById(R.id.txtTitle);
                TextInputLayout txtPId = dialog.findViewById(R.id.txtPId);
                TextInputLayout txtPrice = dialog.findViewById(R.id.txtPrice);
                TextInputLayout txtStock = dialog.findViewById(R.id.txtStock);
                TextInputEditText edtProductId = dialog.findViewById(R.id.edtProductId);
                TextInputEditText edtPrice = dialog.findViewById(R.id.edtPrice);
                TextInputEditText edtStock = dialog.findViewById(R.id.edtStock);
                Button btnInventory = dialog.findViewById(R.id.btnInventory);

                txtTitle.setText("Update Product");
                btnInventory.setText("Update Product");

                edtProductId.setText(String.valueOf(productId));
                edtProductId.setEnabled(false);
                txtPId.setEnabled(false);
                edtPrice.setText(String.valueOf(current.getPrice()));
                edtStock.setText(String.valueOf(current.getStock()));

                btnInventory.setOnClickListener(b -> {
                    String priceStr = edtPrice.getText().toString().trim();
                    String stockStr = edtStock.getText().toString().trim();
                    txtPrice.setError(null);
                    txtStock.setError(null);
                    if (priceStr.isEmpty() || stockStr.isEmpty()) {
                        if (priceStr.isEmpty()) txtPrice.setError("Please enter product price");
                        if (stockStr.isEmpty()) txtStock.setError("Please enter product stock");
                        return;
                    }
                    try {
                        double price = Double.parseDouble(priceStr);
                        int stock = Integer.parseInt(stockStr);
                        if (price <= 0) {
                            txtPrice.setError("Price can not be zero or negative");
                            return;
                        }
                        if (stock < 0) {
                            txtStock.setError("Stock can not be negative");
                            return;
                        }

                        current.setPrice(price);
                        current.setStock(stock);
                        notifyItemChanged(holder.getAdapterPosition());
                        if (listener != null) listener.onDataChanged();
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        txtStock.setError("Invalid price or stock value");
                    }
                });
                dialog.show();
            }
        });

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Remove Product")
                        .setMessage("Are you sure to remove this product from inventory?")
                        .setPositiveButton("Yes", (d, which) -> {
                            int pos = holder.getAdapterPosition();
                            if (pos == RecyclerView.NO_POSITION) return;

                            int productId = idList.get(pos);
                            inventoryMap.remove(productId);   // map se (key se)
                            idList.remove(pos);               // list se (index se)
                            notifyItemRemoved(pos);
                            if (listener != null) listener.onDataChanged();
                            Toast.makeText(context, "Product Removed from Inventory", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductId, tvPrice, tvStock;
        LinearLayout llRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductId = itemView.findViewById(R.id.tvProductId);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
            llRow = itemView.findViewById(R.id.llRow);
        }
    }
}
