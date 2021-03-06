package com.example.pocketwatching.Utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ClaimsXAxisValueFormatter extends ValueFormatter {

    List<Float> datesList;

    public ClaimsXAxisValueFormatter(List<Float> arrayOfDates) {
        this.datesList = arrayOfDates;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Long unixTime = (long) (value * 1000);
        return toDate(unixTime);
    }

    public static String toDate(long unixTime) {
        Date date = new Date();
        date.setTime(unixTime * 1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd");
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(String.valueOf(tz)));
        return sdf.format(date);
    }
}