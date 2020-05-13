package com.example.course.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.course.Activities.Fragments.DetailFragment;
import com.example.course.Activities.Fragments.CarFragment;
import com.example.course.Activities.Fragments.SellHistoryFragment;
import com.example.course.Activities.Fragments.ProvidersFragment;
import com.example.course.Activities.Fragments.SellFragment;
import com.example.course.Activities.Fragments.SupplyFragment;
import com.example.course.Activities.Fragments.SupplyHistoryFragment;
import com.example.course.Activities.Fragments.WarehouseFragment;
import com.example.course.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SellHistoryFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_history);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_history: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupplyHistoryFragment()).commit();
                break;
            }
            case R.id.nav_warehouse:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WarehouseFragment()).commit();
                break;
            }
            case R.id.nav_sell: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SellFragment()).commit();
                break;
            }
            case R.id.providers: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProvidersFragment()).commit();
                break;
            }
            case R.id.add_details: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailFragment()).commit();
                break;
            }
            case R.id.add_car : {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CarFragment()).commit();
                break;
            }
            case R.id.nav_supp : {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupplyFragment()).commit();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
