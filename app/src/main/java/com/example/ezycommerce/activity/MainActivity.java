package com.example.ezycommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ezycommerce.R;
import com.example.ezycommerce.fragment.AccessoriesFragment;
import com.example.ezycommerce.fragment.BusinessFragment;
import com.example.ezycommerce.fragment.CookFragment;
import com.example.ezycommerce.fragment.MysteryFragment;
import com.example.ezycommerce.fragment.ScifiFragment;
import com.example.ezycommerce.model.GetProduct;
import com.example.ezycommerce.network.RESTClient;
import com.example.ezycommerce.network.RESTInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements
        AccessoriesFragment.OnFragmentInteractionListener, BusinessFragment.OnFragmentInteractionListener,
        CookFragment.OnFragmentInteractionListener, MysteryFragment.OnFragmentInteractionListener, ScifiFragment.OnFragmentInteractionListener {

    public String nama;
    private final Fragment mAccessoriesFragment = AccessoriesFragment.newInstance();
    private final Fragment mBusinessFragment = BusinessFragment.newInstance();
    private final Fragment mCookFragment = CookFragment.newInstance();
    private final Fragment mMysteryFragment = MysteryFragment.newInstance();
    private final Fragment mScifiFragment = ScifiFragment.newInstance();
    private final FragmentManager fm = getSupportFragmentManager();

    private Fragment mActiveFragment = mAccessoriesFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_acc:
                    fm.beginTransaction().hide(mActiveFragment).show(mAccessoriesFragment).commit();
                    mActiveFragment = mAccessoriesFragment;
                    return true;

                case R.id.nav_bus:
                    fm.beginTransaction().hide(mActiveFragment).show(mBusinessFragment).commit();
                    mActiveFragment = mBusinessFragment;
                    return true;

                case R.id.nav_cook:
                    fm.beginTransaction().hide(mActiveFragment).show(mCookFragment).commit();
                    mActiveFragment = mCookFragment;
                    return true;

                case R.id.nav_mys:
                    fm.beginTransaction().hide(mActiveFragment).show(mMysteryFragment).commit();
                    mActiveFragment = mMysteryFragment;
                    return true;

                case R.id.nav_sci:
                    fm.beginTransaction().hide(mActiveFragment).show(mScifiFragment).commit();
                    mActiveFragment = mScifiFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

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

        fm.beginTransaction().add(R.id.contentContainer, mAccessoriesFragment).commit();
        fm.beginTransaction().add(R.id.contentContainer, mBusinessFragment).hide(mBusinessFragment).commit();
        fm.beginTransaction().add(R.id.contentContainer, mCookFragment).hide(mCookFragment).commit();
        fm.beginTransaction().add(R.id.contentContainer, mMysteryFragment).hide(mMysteryFragment).commit();
        fm.beginTransaction().add(R.id.contentContainer, mScifiFragment).hide(mScifiFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}