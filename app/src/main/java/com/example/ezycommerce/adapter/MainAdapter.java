package com.example.ezycommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.R;
import com.example.ezycommerce.activity.DetailActivity;
import com.example.ezycommerce.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Product> pList;

    public MainAdapter(List<Product> pList){
        this.pList = pList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int layoutId = R.layout.row;
        View view = inflater.inflate(layoutId, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(pList.get(position).getName());
        holder.priceTV.setText("$" + pList.get(position).getPrice().toString());
        Picasso.get()
                .load(pList.get(position).getImg())
                .into(holder.imageIV);
    }

    @Override
    public int getItemCount() {
        return (pList != null) ? pList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        TextView priceTV;
        ImageView imageIV;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    int pos = getAdapterPosition();
                    Product prod = pList.get(pos);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", prod);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            titleTV = (TextView) itemView.findViewById(R.id.title);
            priceTV = (TextView) itemView.findViewById(R.id.price);
            imageIV = (ImageView) itemView.findViewById(R.id.productImage);
        }
    }
}
