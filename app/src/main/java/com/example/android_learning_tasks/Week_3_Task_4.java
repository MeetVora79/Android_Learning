package com.example.android_learning_tasks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.LinkedHashMap;

public class Week_3_Task_4 extends AppCompatActivity {

    RecyclerInvetoryAdapter adapter;
    RecyclerView recyclerProduct;
    FloatingActionButton btnOpenDialog;
    LinkedHashMap<Integer, InventoryProductData> inventoryMap = new LinkedHashMap<>();
    Dialog dialog;
    LinearLayout llEmpty;
    Button btnAddProduct;
    TextInputLayout txtSearch;

    public void refreshUI() {
        llEmpty.setVisibility(inventoryMap.isEmpty() ? View.VISIBLE : View.GONE);
        txtSearch.setVisibility(inventoryMap.isEmpty() ? View.GONE : View.VISIBLE);
    }
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week3_task4);

        initializeInventory();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Product Inventory");
        }

        llEmpty = findViewById(R.id.llEmpty);
        recyclerProduct = findViewById(R.id.recyclerProduct);
        btnOpenDialog = findViewById(R.id.btnOpenDialog);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        txtSearch = findViewById(R.id.txtSearch);
        TextInputEditText edtSearch = findViewById(R.id.edtSearch);

        edtSearch.setOnEditorActionListener( (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String searchText = edtSearch.getText().toString().trim();
                if (searchText.isEmpty()) {
                    txtSearch.setError("Please enter product id");
                    recyclerProduct.scrollToPosition(0);
                    return false;
                }
                try {
                    int searchId = Integer.parseInt(searchText);
                    int position = adapter.getPositionForId(searchId);

                    if (position != -1) {
                        txtSearch.setError(null);
                        recyclerProduct.scrollToPosition(position);
                    } else {
                        txtSearch.setError("Product ID not found");
                    }
                } catch (NumberFormatException e) {
                    txtSearch.setError("Invalid Product ID");
                }
                return true;
            }
            return false;
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOpenDialog.performClick();
            }
        });

        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerInvetoryAdapter(this, inventoryMap, () -> refreshUI());

        recyclerProduct.setAdapter(adapter);
        refreshUI();

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOpenDialog.animate().rotation(90f).setDuration(500).start();
                    dialog = new Dialog(Week_3_Task_4.this);
                    dialog.setContentView(R.layout.add_invetory_product);
                    dialog.setOnDismissListener(d ->
                            btnOpenDialog.animate().rotation(0f).setDuration(500).start()
                    );
                    dialog.show();

                    TextInputLayout txtPId = dialog.findViewById(R.id.txtPId);
                    TextInputLayout txtPrice = dialog.findViewById(R.id.txtPrice);
                    TextInputLayout txtStock = dialog.findViewById(R.id.txtStock);

                    TextInputEditText edtProductId = dialog.findViewById(R.id.edtProductId);
                    TextInputEditText edtPrice = dialog.findViewById(R.id.edtPrice);
                    TextInputEditText edtStock = dialog.findViewById(R.id.edtStock);
                    Button btnInventory = dialog.findViewById(R.id.btnInventory);

                    btnInventory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String idStr = edtProductId.getText().toString().trim();
                            String priceStr = edtPrice.getText().toString().trim();
                            String stockStr = edtStock.getText().toString().trim();
                            txtPId.setError(null);
                            txtPrice.setError(null);
                            txtStock.setError(null);
                            if (idStr.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                                if (idStr.isEmpty()) txtPId.setError("Please enter product id");
                                if (priceStr.isEmpty()) txtPrice.setError("Please enter product price");
                                if (stockStr.isEmpty()) txtStock.setError("Please enter product quantity");
                                return;
                            }
                            try {
                                int productId = Integer.parseInt(idStr);
                                double price = Double.parseDouble(priceStr);
                                int stock = Integer.parseInt(stockStr);

                                if(price < 0){
                                    txtPrice.setError("Price can not be negative");
                                }
                                if(stock < 0){
                                    txtStock.setError("Stock can not be negative");
                                }
                                boolean added = adapter.addProduct(productId, new InventoryProductData(price, stock));
                                if (!added) {
                                    txtPId.setError("Product ID " + productId + " already exists");
                                    return;
                                }
                                recyclerProduct.scrollToPosition(adapter.getItemCount() - 1);
                                dialog.dismiss();
                            } catch (NumberFormatException e) {
                                txtPId.setError("Invalid product id");
                            }
                        }
                    });
                    dialog.show();
                }
            });
        }

    private void initializeInventory() {
        inventoryMap.put(101, new InventoryProductData(1000.0, 10));
        inventoryMap.put(102, new InventoryProductData(2000.0, 20));
        inventoryMap.put(103, new InventoryProductData(3000.0, 30));
        inventoryMap.put(104, new InventoryProductData(4000.0, 40));
        inventoryMap.put(105, new InventoryProductData(5000.0, 50));
        inventoryMap.put(106, new InventoryProductData(6000.0, 60));
        inventoryMap.put(107, new InventoryProductData(7000.0, 70));
        inventoryMap.put(108, new InventoryProductData(8000.0, 80));
        inventoryMap.put(109, new InventoryProductData(9000.0, 90));
        inventoryMap.put(110, new InventoryProductData(10000.0, 100));
    }
}
