package com.example.meghamoondra.navigationbar.view.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.view.activity.HomeActivity;
import com.example.meghamoondra.navigationbar.view.activity.MainActivity;
import com.example.meghamoondra.navigationbar.view.activity.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.productUrl;


public class HomeFragment extends Fragment implements ProductAdapter.IAdapterCommunicator {
    List<Product> productList;
    RecyclerView recyclerView;
    IAccount iAccount;
    ProductAdapter productadapter;

//    public HomeFragment()

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
        iAccount = AppController.getInstance().getClientProduct(productUrl).create(IAccount.class);

        getAllProducts();


        productadapter = new ProductAdapter(productList,this);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));


        //setting adapter to recyclerview
        myrv.setAdapter(productadapter);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getAllProducts() {
        Call<Product[]> call = iAccount.getAll();
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {


                if (response.code() >= 200 && response.code() < 300) {
                    productList.addAll(Arrays.asList(response.body()));

                    productadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
               // Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                Intent intent;
//                intent = new Intent(getActivity(),SearchActivity.class);
//                intent.putExtra("Search","electronic");
//                startActivity(intent);
                Fragment frag = new SearchFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.replace(R.id.screen_area, frag);
                ft.commit();
//                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                alertDialog.setTitle("Sorry");
//                alertDialog.setMessage("Server not responding");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();

            }
        });

    }


    @Override
    public void deleteItem(int position) {

    }


    @Override
    public void navigateDetail(Product product) {
        ((MainActivity)getActivity()).navigateToProductDetails(product);
    }
}



