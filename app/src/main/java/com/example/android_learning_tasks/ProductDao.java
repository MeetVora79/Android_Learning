package com.example.android_learning_tasks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductModel_w6_t1 product);

    @Delete
    void delete(ProductModel_w6_t1 product);

    @Query("select * from products")
    LiveData<List<ProductModel_w6_t1>> getFavProducts();

    @Query("select * from products where id = :id")
    ProductModel_w6_t1 getProductById(int id);
}
