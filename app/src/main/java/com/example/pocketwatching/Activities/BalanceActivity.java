package com.example.pocketwatching.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Etc.Utils;
import com.example.pocketwatching.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BalanceActivity extends AppCompatActivity {
    private LineChart mChart;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_balance_graph);

        mChart = findViewById(R.id.mChart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        configureLineChart();
        ArrayList<Entry> fakeData = (ArrayList<Entry>) generateData();
        setLineChartData(fakeData);
    }

    private List<Entry> generateData() {
        ArrayList<Entry> fakeData = new ArrayList<>();
        fakeData.add(new Entry(1, 10));
//        fakeData.add(new Entry(16568960253F, 30));
//        fakeData.add(new Entry(1657068825F, 20));
        return fakeData;
    }

    private void configureLineChart() {
        Description desc = new Description();
        desc.setText("");
        mChart.setDescription(desc);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("MMM dd", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {
                Date date = new Date();
                Log.i("current value", String.valueOf(value));
                date.setTime((long) value);
                mFormat.format(date);
                return String.valueOf(date);
            }
        });
    }

    private void setLineChartData(ArrayList<Entry> pricesHigh) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet highLineDataSet = new LineDataSet(pricesHigh, "STONKS");
        highLineDataSet.setDrawCircles(true);
        highLineDataSet.setCircleRadius(4);
        highLineDataSet.setDrawValues(false);
        highLineDataSet.setLineWidth(3);
        highLineDataSet.setColor(Color.GREEN);
        highLineDataSet.setCircleColor(Color.GREEN);
        dataSets.add(highLineDataSet);

        LineData lineData = new LineData(dataSets);
        mChart.setData(lineData);
        mChart.invalidate();
    }
}

