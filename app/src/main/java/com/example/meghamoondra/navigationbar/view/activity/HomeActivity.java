package com.example.meghamoondra.navigationbar.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.view.activity.fragment.HomeFragment;
import com.example.meghamoondra.navigationbar.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.commit();
    }

    public void navigateToProductDetails(Product product){
        Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}
