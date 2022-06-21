package com.example.pocketwatching.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvTest = findViewById(R.id.tvTest);
        tvTest.setText("cheese");
    }
}
