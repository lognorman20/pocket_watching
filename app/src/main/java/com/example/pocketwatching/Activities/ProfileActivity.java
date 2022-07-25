package com.example.pocketwatching.Activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pocketwatching.Fragments.ProfileFragment;
import com.example.pocketwatching.Fragments.SearchFragment;
import com.example.pocketwatching.Fragments.SortingFragment;
import com.example.pocketwatching.Models.Ethplorer.Token;
import com.example.pocketwatching.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public List<Token> tokens = null;

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
                        Bundle tokenBundle = new Bundle();
                        tokenBundle.putSerializable("tokens", (Serializable) tokens);
                        fragment.setArguments(tokenBundle);
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

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return this.tokens;
    }
}