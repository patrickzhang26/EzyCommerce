package com.example.ezycommerce.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.R;
import com.example.ezycommerce.adapter.CartAdapter;
import com.example.ezycommerce.database.DatabaseHelper;
import com.example.ezycommerce.model.GetProduct;
import com.example.ezycommerce.model.Product;
import com.example.ezycommerce.network.RESTClient;
import com.example.ezycommerce.network.RESTInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {

    public String nama;
    private DatabaseHelper db;
    private RecyclerView productRecycler;
    private List<Product> cList;
    private CartAdapter cAdapter;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        productRecycler = findViewById(R.id.recyclerView);

        ArrayList<Product> newList = new ArrayList<>();
        newList.addAll(db.allProduct());
        cAdapter = new CartAdapter(newList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CartActivity.this);
        productRecycler.setLayoutManager(layoutManager);
        productRecycler.setAdapter(cAdapter);

        TextView subTV;
        TextView taxTV;
        TextView totalTV;
        double subTot;
        double tax;
        double tot;
        String subTotal;
        String taxes;
        String total;

        subTV = findViewById(R.id.subTotal);
        taxTV = findViewById(R.id.tax);
        totalTV = findViewById(R.id.total);

        subTot = db.getSumValue();
        subTotal = String.format("%.2f",subTot);
        subTV.setText("$" + subTotal);

        tax = getTax(subTot);
        taxes = String.format("%.2f",tax);
        taxTV.setText("$"+ taxes);

        tot = getTotal(subTot,tax);
        total = String.format("%.2f",tot);
        totalTV.setText("$" + total);

        Button cancelBtn = findViewById(R.id.cnlBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        Button nextBtn = findViewById(R.id.nxtBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                Intent intent = new Intent(CartActivity.this, CompleteActivity.class);
                startActivity(intent);
            }
        });

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    public double getTax(double sub){
        double tax = 0;
        tax = sub * 0.02;

        return tax;
    }

    public double getTotal(double sub, double tax){
        double tot;
        tot = sub + tax;

        return tot;
    }
}
