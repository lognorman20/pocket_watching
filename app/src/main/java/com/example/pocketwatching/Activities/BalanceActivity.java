package com.example.pocketwatching.Activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.R;

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;


public class BalanceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_balance_graph);

        LineView lineView = (LineView)findViewById(R.id.line_view);

        // init x axis labels
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            labels.add(String.valueOf(i));
        }

        lineView.setBottomTextList(labels);
        lineView.setColorArray(new int[]{
                Color.BLACK,Color.GREEN,Color.GRAY,Color.CYAN});

       ArrayList<Integer> data = new ArrayList<>();
       for (int i = 0; i < 5; i++) {
           data.add(i * 5);
       }

       ArrayList<ArrayList<Integer>> dataList = new ArrayList<>();
       dataList.add(data);

        lineView.setDataList(dataList); //or lineView.setFloatDataList(floatDataLists)
    }
}
