package com.example.pocketwatching.Activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.R;
import com.github.mikephil.charting.charts.LineChart;

public class BalanceActivity extends AppCompatActivity {
    private LineChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_balance_graph);

        chart = findViewById(R.id.lineChart);
        chart.setBackgroundColor(Color.RED);

    }
}
