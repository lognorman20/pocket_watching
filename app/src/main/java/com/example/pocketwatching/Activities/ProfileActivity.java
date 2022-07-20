package com.example.pocketwatching.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pocketwatching.Fragments.ProfileFragment;
import com.example.pocketwatching.Fragments.SearchFragment;
import com.example.pocketwatching.Fragments.SortingFragment;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;
import com.example.pocketwatching.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    public ProfileActivity() {}

    /**************************************************/
    /***************** Core Functions *****************/
    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_sorting:
                        fragment = new SortingFragment();

                        ArrayList<Token> tokens = (ArrayList<Token>) ProfileFragment.getInstance().getValuableTokens();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tokens", (Serializable) tokens);
                        fragment.setArguments(bundle);

                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
    }
}