package com.example.meghamoondra.navigationbar.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.controller.AppController;
import com.example.meghamoondra.navigationbar.controller.IAccount;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.model.SearchProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.meghamoondra.navigationbar.controller.Urls.productUrl;

public class SearchAdapterFragment extends RecyclerView.Adapter<SearchAdapterFragment.ViewHolder> {

    private static IAccount iAccount;
    Product productDetails = new Product();

    private List<SearchProduct> mProduct;
    private IAdapterCommunicator iAdapterCommunicator;
    //IAccount iAccount;
    public SearchAdapterFragment(List<SearchProduct> products, IAdapterCommunicator iAdapterCommunicator) {
        mProduct = products;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Input the layout id
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SearchProduct product = mProduct.get(position);
        holder.productName.setText(product.getProductName());
       // holder.productDesc.setText(product.getDescription().substring(0, Math.min(product.getDescription().length(), 50)) + "...");
        //holder.productRating.setText("Merchants Selling: " + product.getMerchantId().size());
        holder.productPrice.setText("₹ " + product.getPrice().toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InsideCardView","this");
                //final Product product1 = (Product) product;

                productDetails.setAttribute(product.getAttribute());
                productDetails.setDescription(product.getDescription());
                productDetails.setImgUrl(product.getImgUrl());
                productDetails.setProductName(product.getProductName());
                productDetails.setPrice(product.getPrice());
                productDetails.setId(product.getId());
                productDetails.setMerchantId(product.getMerchantId());

                iAdapterCommunicator.navigateDetail(productDetails);
            }
        });

        Glide.with(holder.imageView.getContext())
                .load(product.getImgUrl())
                .into(holder.imageView);
    }


    //Binding UI



    @Override
    public int getItemCount() {
        // Set the list count

        return mProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Declare the views
        LinearLayout cardView;
        TextView productName;
        TextView productPrice;
        ImageView imageView;
        //Button btDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.home_card);
            productName = itemView.findViewById(R.id.product_title);
            //productDesc = itemView.findViewById(R.id.tv_description);
            //productRating = itemView.findViewById(R.id.tv_sellers);
            productPrice = itemView.findViewById(R.id.product_price);
            imageView = itemView.findViewById(R.id.product_img);
            //btDelete = itemView.findViewById(R.id.bt_delete);

        }
    }

//    public void getProductsDetails(String productId){
//        iAccount = AppController.getInstance().getClientProduct(productUrl).create(IAccount.class);
//        Call<Product> call = iAccount.get(productId);
//        //Log.d("anything", newAccount.toString());
//
//        call.enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//                productDetails = response.body();
//                iAdapterCommunicator.navigateDetail(productDetails);
//
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//                Log.d("searchAdapter","On Failure from Search Adapter");
//
//            }
//        });
//    }


    public interface IAdapterCommunicator {
        void deleteItem(int position);
        void navigateDetail(Product product);
    }
}
