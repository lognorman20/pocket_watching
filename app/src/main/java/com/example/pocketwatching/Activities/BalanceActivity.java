package com.example.pocketwatching.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.R;

public class BalanceActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_balance_graph);

        Toast.makeText(this, "We made it to the screen bro", Toast.LENGTH_SHORT).show();
    }
}

