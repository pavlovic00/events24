package com.pavlovic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.pavlovic.cubes.events24.ui.fragment.FavouritesFragment;
import com.pavlovic.cubes.events24.ui.fragment.HomeFragment;
import com.pavlovic.cubes.events24.ui.fragment.MapsFragment;
import com.pavlovic.cubes.events24.ui.fragment.ProfileFragment;
import com.pavlovic.cubes.events24.ui.fragment.SearchFragment;
import com.pavlovic.cubes.events24.ui.fragment.TicketsFragment;
import com.pavlovic.cubes.events24.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pavlovic.cubes.events24.databinding.ActivityHomeBinding;

public class HomeActivity extends BasicActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean isStartedFromProfile = getIntent().getBooleanExtra("start_profile",false);

        if (isStartedFromProfile){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ProfileFragment.newInstance()).commit();
            binding.bottomNavigationView.setSelectedItemId(R.id.profile);
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit();
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case R.id.search:
                        selectedFragment = SearchFragment.newInstance();
                        break;
                    case R.id.map:
                        selectedFragment = MapsFragment.newInstance();
                        break;
                    case R.id.favourites:
                        selectedFragment = FavouritesFragment.newInstance();
                        break;
                    case R.id.profile:
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,selectedFragment)
                        .commit();

                return true;
            }
        });


    }
}