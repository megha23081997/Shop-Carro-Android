package com.example.meghamoondra.navigationbar.view.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.model.SearchProduct;
import com.example.meghamoondra.navigationbar.view.activity.MainActivity;
import com.example.meghamoondra.navigationbar.view.activity.ProductDetailsActivity;
import com.example.meghamoondra.navigationbar.view.activity.SearchActivity;
import com.example.meghamoondra.navigationbar.view.activity.SearchAdapterFragment;
import com.example.meghamoondra.navigationbar.view.activity.adapter.ProductAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.meghamoondra.navigationbar.controller.Urls.productUrl;
import static com.example.meghamoondra.navigationbar.controller.Urls.searchUrl;


public class SearchFragment extends Fragment implements SearchAdapterFragment.IAdapterCommunicator {
    List<SearchProduct> productList;
    RecyclerView recyclerView;
    IAccount iAccount;
    SearchAdapterFragment searchAdapterFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView myrv =  view.findViewById(R.id.rv_products);

        //initializing the productlist
        productList = new ArrayList<>();
        iAccount = AppController.getInstance().getClientSearch(searchUrl).create(IAccount.class);

        getAllSearchProducts("electronics");


        searchAdapterFragment = new SearchAdapterFragment(productList,this);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));


        //setting adapter to recyclerview
        myrv.setAdapter(searchAdapterFragment);


    }

    private void getAllSearchProducts(String query) {
        Log.d(TAG, "getAllSearchProducts: ");
        Call<SearchProduct[]> call = iAccount.getSearchProducts(query);
        call.enqueue(new Callback<SearchProduct[]>() {
            @Override
            public void onResponse(Call<SearchProduct[]> call, Response<SearchProduct[]> response) {
                Log.d("search1", "onResponse: "+response.body().toString());
                Log.d(TAG, "onResponse: " + productList);
                if (response.code() >= 200 && response.code() < 300 && response.body()!=null) {
                    Log.d(TAG, "onResponse: " + productList.size());
                    productList.addAll(Arrays.asList(response.body()));
                    if(productList.size() == 0) {
                        Log.d(TAG, "onResponse: " + "insde");
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage("Search Results Empty");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        //finish();
                                    }
                                });
                        alertDialog.show();
                    }
                    Log.d("search", "onResponse: "+response.body().toString());

                    searchAdapterFragment.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<SearchProduct[]> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request().url());
                Toast.makeText(getActivity(), "Search Failure" , Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Sorry");
                alertDialog.setMessage("Search Not Responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                //finish();
                            }
                        });
                alertDialog.show();
            }
        });

    }
    @Override
    public void deleteItem(int position) {

    }


    @Override
    public void navigateDetail(Product product) {
        Intent intent=new Intent(getActivity(),ProductDetailsActivity.class);
        intent.putExtra("product", product);
         //intent.putExtra("ImageUrl",imageUrl);
        startActivity(intent);
    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}



