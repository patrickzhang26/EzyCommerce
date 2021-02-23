package com.example.ezycommerce.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezycommerce.R;
import com.example.ezycommerce.database.DatabaseHelper;
import com.example.ezycommerce.model.GetProduct;
import com.example.ezycommerce.model.Product;
import com.example.ezycommerce.network.RESTClient;
import com.example.ezycommerce.network.RESTInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    public String nama;
    TextView prodTitle;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImage;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new DatabaseHelper(this);

        prodTitle = findViewById(R.id.productName);
        prodPrice = findViewById(R.id.productPrice);
        prodDesc = findViewById(R.id.productDesc);
        prodImage = findViewById(R.id.productImage);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Product prods = (Product) bundle.getSerializable("product");

        assert prods != null;
        prodTitle.setText(prods.getName());
        prodPrice.setText("$" + prods.getPrice().toString());
        prodDesc.setText(prods.getDescription());
        Picasso.get().load(prods.getImg())
                .into(prodImage);

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

        Button button = findViewById(R.id.buyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addProduct(prods);
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

}
