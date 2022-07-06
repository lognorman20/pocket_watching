package com.example.pocketwatching.Etc;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class ClaimsYAxisValueFormatter extends ValueFormatter {
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Long point = (long) value;
        return point.toString();
    }
}
