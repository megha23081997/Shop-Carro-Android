package com.example.meghamoondra.navigationbar.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.model.SearchAdapter;
import com.example.meghamoondra.navigationbar.model.SearchProduct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.meghamoondra.navigationbar.controller.Urls.searchUrl;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.IAdapterCommunicator {

    private static final String TAG = "SearchActivity";
    List<SearchProduct> productList;
    IAccount iAccount;
    SearchAdapter searchadapter;
    //the recyclerview
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<SearchProduct>();
        iAccount = AppController.getInstance().getClientSearch(searchUrl).create(IAccount.class);
        String data = getIntent().getExtras().getString("Search","defaultKey").toString();
        //data = "some";
        Log.d(TAG, "onCreate: i am going to call getallsearch");
        getAllSearchProducts(data);


        //creating recyclerview adapter
        searchadapter = new SearchAdapter(productList,this);

        //setting adapter to recyclerview
        recyclerView.setAdapter(searchadapter);
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
                        AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage("Search Results Empty");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                    Log.d("search", "onResponse: "+response.body().toString());

                    searchadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<SearchProduct[]> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request().url());
                Toast.makeText(getApplicationContext(), "Search Failure" , Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();
                alertDialog.setTitle("Sorry");
                alertDialog.setMessage("Search Not Responding");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
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
        Intent intent=new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("product", product );
       // intent.putExtra("ImageUrl",imageUrl);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
