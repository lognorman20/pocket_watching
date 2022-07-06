package com.example.pocketwatching.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Etc.ClaimsXAxisValueFormatter;
import com.example.pocketwatching.Etc.ClaimsYAxisValueFormatter;
import com.example.pocketwatching.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BalanceActivity extends AppCompatActivity {
    private LineChart volumeReportChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_balance_graph);

        volumeReportChart = findViewById(R.id.reportingChart);
        setupChart();
        volumeReportChart.invalidate();
    }

    private void setupChart() {
        XAxis xAxis = volumeReportChart.getXAxis();
        YAxis leftAxis = volumeReportChart.getAxisLeft();
        volumeReportChart.getAxisRight().setEnabled(false);

        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);

        volumeReportChart.getDescription().setEnabled(false);
        volumeReportChart.setTouchEnabled(false); // TODO: Change to make scrollable
        List<Float> floats = getX();
        xAxis.setValueFormatter(new ClaimsXAxisValueFormatter(floats));
        leftAxis.setValueFormatter(new ClaimsYAxisValueFormatter());

        LineDataSet set1;
        List<Entry> values = makeEntries(floats);
        set1 = new LineDataSet(values, "Portfolio Value");

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // text size of values, set to zero to hide
        set1.setValueTextSize(0f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        volumeReportChart.setData(data);
    }

    private List<Float> getX() {
        List<Long> dataList = new ArrayList<Long>() {{
            add(Long.valueOf(1656820787));
            add(Long.valueOf(1656907187));
            add(Long.valueOf(1656993587));
            add(Long.valueOf(1656820787));
            add(Long.valueOf(1657079987));
        }};

        // convert values to a float
        List<Float> floats = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            float seconds = (float) (dataList.get(i) / 1000);
            floats.add(seconds);
        }
        return floats;
    }

    private List<Entry> makeEntries(List<Float> floats) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            values.add(new Entry(floats.get(i), i * 5));
        }
        return values;
    }
}
