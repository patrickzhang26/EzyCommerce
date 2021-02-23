package com.example.ezycommerce.network;

import com.example.ezycommerce.model.GetProduct;
import com.example.ezycommerce.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTInterface {

    @GET("book?nim=2201745212&nama=PatrickDithamanoTjuatja")
    Call<GetProduct> getProducts();

    @GET("book/{bookId}")
    Call<Product> getProduct(@Path("bookId") int bookId, @Query("nim") String nim, @Query("nama") String nama);
}
