package com.example.meghamoondra.navigationbar.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.view.activity.fragment.HomeFragment;
import com.example.meghamoondra.navigationbar.R;
import com.example.meghamoondra.navigationbar.view.activity.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private static final String ShopcarroPref = "ShopCarroPref";
    private String emailId;

//
private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ShopCarro");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        Menu menu = navigationView.getMenu();

        MenuItem nav_login = menu.findItem(R.id.nav_login);
        MenuItem nav_signUp = menu.findItem(R.id.nav_signup);
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);

        MenuItem nav_myCart = menu.findItem(R.id.nav_cart);
        MenuItem nav_myOrder = menu.findItem(R.id.nav_order);

        TextView nav_email = headerView.findViewById(R.id.text_email);
        //TextView welcome = findViewById(R.id.welcome);

        nav_logout.setVisible(false);
        nav_myCart.setVisible(false);
        nav_myOrder.setVisible(false);

        if(sharedPreferences.contains("username")){
            if(sharedPreferences.getString("username",null) != null){
                nav_login.setVisible(false);
                nav_signUp.setVisible(false);
                nav_logout.setVisible(true);
                nav_myCart.setVisible(true);
                nav_myOrder.setVisible(true);
                Log.d(TAG, "onCreate: " + sharedPreferences.getString("username", null));
               // emailId=sharedPreferences.getString("username",null);

                //nav_email.setText(emailId);
                //welcome.setText("Welcome to ShopCarro!!");
                nav_email.setText(sharedPreferences.getString("username", "Guest User"));
            }
        }

        //SearchView Setup
        SearchView searchView = findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent;
                intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("Search",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        Fragment fragment = new HomeFragment();
        Fragment frag = new SearchFragment();
        if(fragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }

        else{


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, frag);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        SharedPreferences sharedPreferences = getSharedPreferences(ShopcarroPref, Context.MODE_PRIVATE);
        Fragment fragment=null;
        String username = sharedPreferences.getString("username","null");
        Log.d("anything",username);
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setTitle("ShopCarro");
            fragment = new HomeFragment();
            // Handle the Home action
        } else if (id == R.id.nav_furniture) {
            //toolbar.setTitle("Appliance");
            Intent intent;
            intent = new Intent(MainActivity.this,SearchActivity.class);
            intent.putExtra("Search","furniture");
            startActivity(intent);

        } else if (id == R.id.nav_fashion) {
            //toolbar.setTitle("Fashion");
            Intent intent;
            intent = new Intent(MainActivity.this,SearchActivity.class);
            intent.putExtra("Search", "fashion");
            startActivity(intent);

        } else if (id == R.id.nav_electronic) {
            //toolbar.setTitle("Electronics");
            Intent intent;
            intent = new Intent(MainActivity.this,SearchActivity.class);
            intent.putExtra("Search","electronic");
            startActivity(intent);

        } else if (id == R.id.nav_cart) {
            //toolbar.setTitle("Cart");
            Intent cart = new Intent(this,CartActivity.class);
            startActivity(cart);

        } else if (id == R.id.nav_order) {
            //toolbar.setTitle("Order");
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signup){
           // fragment = new SignUpFragment();
            Intent signup = new Intent(this,SignUpActivity.class);
            startActivity(signup);

//            toolbar.setTitle("Signup");
        }else if (id == R.id.nav_login){
           //fragment=new Login_page();
            Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);
            //toolbar.setTitle("Login");
        }
        else if (id == R.id.nav_logout){
            if(sharedPreferences.contains("username")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",null);
                editor.putString("password",null);
                editor.commit();

            }
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

//        if(fragment!=null){
//            FragmentManager fragmentManager=getSupportFragmentManager();
//            FragmentTransaction ft= fragmentManager.beginTransaction();
//            ft.replace(R.id.screen_area,fragment);
//            ft.commit();
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigateToProductDetails(Product product){
        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}
