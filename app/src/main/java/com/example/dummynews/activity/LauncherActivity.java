package com.example.dummynews.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dummynews.R;
import com.example.dummynews.fragment.HomeFragment;
import com.example.dummynews.fragment.NewsListFragment;
import com.example.dummynews.fragment.NewsTabFragment;

public class LauncherActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


            drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolBar);
        frameLayout = findViewById(R.id.contentFrame);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new HomeFragment();
        fragmentTransaction.add(R.id.contentFrame,fragment);
        fragmentTransaction.commit();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        //To add a listener on the DrawerLayout the following method is used.
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String title = (String) item.getTitle();
                setTitle(title);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        // close drawer when item is tapped
                        fragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.technology:
                        // close drawer when item is tapped
                        fragment = new NewsTabFragment("technology");
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();

                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.business:
                        Toast.makeText(LauncherActivity.this, "Business is clicked", Toast.LENGTH_SHORT).show();
                        fragment = new NewsTabFragment("business");
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();
                        // close drawer when item is tapped

                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.sports:
                        Toast.makeText(LauncherActivity.this, "Sports is clicked", Toast.LENGTH_SHORT).show();
                        fragment = new NewsTabFragment("sports");
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();
                        // close drawer when item is tapped

                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.entertainment:
                        Toast.makeText(LauncherActivity.this, "Entertainment is clicked", Toast.LENGTH_SHORT).show();
                        fragment = new NewsTabFragment("entertainment");
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();
                        // close drawer when item is tapped

                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.by_country:
                        Toast.makeText(LauncherActivity.this, "By country is clicked", Toast.LENGTH_SHORT).show();
                        fragment = new NewsTabFragment("country");
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();
                        // close drawer when item is tapped

                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.saved:
                        fragment = new NewsListFragment("saved");
                        fragmentTransaction.replace(R.id.contentFrame,fragment);
                        fragmentTransaction.commit();
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();
                        return true;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }
}
