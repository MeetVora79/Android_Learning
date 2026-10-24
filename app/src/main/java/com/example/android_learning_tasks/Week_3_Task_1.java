package com.example.android_learning_tasks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Week_3_Task_1 extends AppCompatActivity {

    ReacyclerProductAdapter adapter;
    RecyclerView recyclerProduct;
    FloatingActionButton btnOpenDialog;
    ArrayList<ProductModel> productList = new ArrayList<>();
    Dialog dialog;
    TextView txtGrandTotal;
    LinearLayout llGTotal, llEmpty;
    Button btnAddProduct;

    public void refreshUI() {
        if (productList.isEmpty()) {
            llEmpty.setVisibility(View.VISIBLE);
            llGTotal.setVisibility(View.INVISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
            llGTotal.setVisibility(View.VISIBLE);

            double grandTotal = 0.0;
            for (ProductModel product : productList) {
                grandTotal += product.getSubTotal();
            }
            txtGrandTotal.setText(String.format("%.2f", grandTotal));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week3_task1);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Cart List");
        }

        llGTotal = findViewById(R.id.llGTotal);
        llEmpty = findViewById(R.id.llEmpty);
        txtGrandTotal = findViewById(R.id.txtGrandTotal);
        recyclerProduct = findViewById(R.id.recyclerProduct);
        btnOpenDialog = findViewById(R.id.btnOpenDialog);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOpenDialog.performClick();
            }
        });

        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReacyclerProductAdapter(this, productList, new ReacyclerProductAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged() {
                refreshUI();
            }
        });
        recyclerProduct.setAdapter(adapter);

        refreshUI();

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOpenDialog.animate().rotation(90f).setDuration(500).start();
                dialog = new Dialog(Week_3_Task_1.this);
                dialog.setContentView(R.layout.add_product);
                dialog.setOnDismissListener(d ->
                        btnOpenDialog.animate().rotation(0f).setDuration(500).start()
                );
                dialog.show();

                EditText edtProduct = dialog.findViewById(R.id.edtProduct);
                EditText edtPrice = dialog.findViewById(R.id.edtPrice);
                Button btnCart = dialog.findViewById(R.id.btnCart);

                btnCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String productName = edtProduct.getText().toString().trim();
                        String productPriceTxt = edtPrice.getText().toString().trim();
                        if (productName.isEmpty() || productPriceTxt.isEmpty()) {
                            if (productName.isEmpty()) edtProduct.setError("Please enter product name");
                            if (productPriceTxt.isEmpty()) edtPrice.setError("Please enter product price");
                        } else {
                            double productPrice = Double.parseDouble(productPriceTxt);
                            productList.add(new ProductModel(productName, productPrice, 1));
                            adapter.notifyItemInserted(productList.size() - 1);
                            recyclerProduct.scrollToPosition(productList.size() - 1);
                            refreshUI();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}
