package com.example.android_learning_tasks;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApiService {
    @GET("products")
    Call<ProductResponse> getProducts();
}
