package com.example.android_learning_tasks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApiService {
    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("products/{id}")
    Call<ProductModel_w6_t1> getProductDetail(@Path("id") int id);

    @GET("products/search")
    Call<ProductResponse> searchProducts(@Query("q") String query);
}
