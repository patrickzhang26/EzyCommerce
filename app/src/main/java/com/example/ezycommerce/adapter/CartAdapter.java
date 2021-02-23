package com.example.ezycommerce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.R;
import com.example.ezycommerce.activity.CartActivity;
import com.example.ezycommerce.activity.DetailActivity;
import com.example.ezycommerce.database.DatabaseHelper;
import com.example.ezycommerce.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Product> cList;

    public CartAdapter(List<Product> cList){
        this.cList = cList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int layoutId = R.layout.cart;
        View view = inflater.inflate(layoutId, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(cList.get(position).getName());
        holder.priceTV.setText("$" + cList.get(position).getPrice().toString());
        Picasso.get()
                .load(cList.get(position).getImg())
                .into(holder.imageIV);
    }

    @Override
    public int getItemCount() {
        return (cList != null) ? cList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV;
        TextView priceTV;
        ImageView imageIV;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            titleTV = (TextView) itemView.findViewById(R.id.title);
            priceTV = (TextView) itemView.findViewById(R.id.price);
            imageIV = (ImageView) itemView.findViewById(R.id.productImage);
        }
    }
}
