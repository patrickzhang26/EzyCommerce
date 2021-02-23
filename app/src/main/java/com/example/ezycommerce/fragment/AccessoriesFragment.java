package com.example.ezycommerce.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezycommerce.R;
import com.example.ezycommerce.adapter.MainAdapter;
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

public class AccessoriesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView productRecycler;
    private MainAdapter pAdapter;
    private List<Product> pList;

    public AccessoriesFragment(){
    }

    public static AccessoriesFragment newInstance(){
        return new AccessoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accessories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productRecycler = (RecyclerView) view.findViewById(R.id.recyclerView);
        getAccessories();
    }

    public void getAccessories(){
        // Fetching accessories data from REST API
        Retrofit retrofit= RESTClient.getRestClient();
        RESTInterface restInterface = retrofit.create(RESTInterface.class);
        Call<GetProduct> call = restInterface.getProducts();

        call.enqueue(new Callback<GetProduct>() {
            @Override
            public void onResponse(Call<GetProduct> call, Response<GetProduct> response) {
                pList = response.body().getProducts();
                getCategory("accessories");
            }

            @Override
            public void onFailure(Call<GetProduct> call, Throwable t) {

            }
        });
    }

    public void getCategory(String cat){
        ArrayList<Product> newProductList = new ArrayList<Product>();

        for(int i=0; i < pList.size(); i++){
            if(pList.get(i).getCategory().toLowerCase().contains(cat)){
                newProductList.add(pList.get(i));
            }
        }

        pAdapter = new MainAdapter(newProductList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AccessoriesFragment.this.getContext());
        productRecycler.setLayoutManager(layoutManager);
        productRecycler.setAdapter(pAdapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccessoriesFragment.OnFragmentInteractionListener) {
            mListener = (AccessoriesFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.must_implement_on_fragment_interaction_listener));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
