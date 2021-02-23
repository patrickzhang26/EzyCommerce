package com.example.ezycommerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ezycommerce.R;
import com.example.ezycommerce.model.GetProduct;
import com.example.ezycommerce.network.RESTClient;
import com.example.ezycommerce.network.RESTInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompleteActivity extends AppCompatActivity {

    public String nama;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        TextView username = findViewById(R.id.username);

        Retrofit retrofit= RESTClient.getRestClient();
        RESTInterface restInterface = retrofit.create(RESTInterface.class);
        Call<GetProduct> call = restInterface.getProducts();

        call.enqueue(new Callback<GetProduct>() {
            @Override
            public void onResponse(Call<GetProduct> call, Response<GetProduct> response) {
                nama = response.body().getNama();
                username.setText(nama);
            }

            @Override
            public void onFailure(Call<GetProduct> call, Throwable t) {

            }
        });

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
}
