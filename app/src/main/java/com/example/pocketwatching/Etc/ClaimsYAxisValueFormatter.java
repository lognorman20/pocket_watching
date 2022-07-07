package com.example.pocketwatching.Etc;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;

public class ClaimsYAxisValueFormatter extends ValueFormatter {
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Log.i("checking y", String.valueOf(value));
        return NumberFormat.getIntegerInstance().format(value);
    }
}
