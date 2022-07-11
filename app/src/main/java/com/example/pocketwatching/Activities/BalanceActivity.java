package com.example.pocketwatching.Activities;

import androidx.appcompat.app.AppCompatActivity;

public class BalanceActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.historical_balance_graph);
//
//        volumeReportChart = findViewById(R.id.reportingChart);
//        setupChart();
//    }
//
//    private void setupChart() {
//        XAxis xAxis = volumeReportChart.getXAxis();
//
//        volumeReportChart.getAxisLeft().setEnabled(false);
//        volumeReportChart.getAxisRight().setEnabled(false);
//        volumeReportChart.getAxisRight().setAxisMaximum(10);
//        volumeReportChart.getDescription().setEnabled(false);
//        volumeReportChart.setTouchEnabled(true);
//        volumeReportChart.setDragEnabled(true);
//        volumeReportChart.animateY(500, Easing.EaseInCubic);
//
//        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
//        xAxis.setPosition(position);
//
//        List<Float> floats = getX();
//        xAxis.setValueFormatter(new ClaimsXAxisValueFormatter(floats));
//
//        LineDataSet set1;
//        List<Entry> values = makeEntries(floats);
//        set1 = new LineDataSet(values, "Portfolio Value");
//
//        // black lines and points
//        set1.setColor(Color.BLACK);
//        set1.setCircleColor(Color.BLACK);
//
//        // line thickness and point size
//        set1.setLineWidth(1f);
//        set1.setCircleRadius(0f);
//
//        // draw points as solid circles
//        set1.setDrawCircleHole(false);
//
//        // hide values about plotted points
//        set1.setValueTextSize(0);
//
//        // set the filled area
//        set1.setDrawFilled(true);
//        set1.setFillFormatter(new IFillFormatter() {
//            @Override
//            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                return volumeReportChart.getAxisLeft().getAxisMinimum();
//            }
//        });
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set1);
//        LineData data = new LineData(dataSets);
//        volumeReportChart.setData(data);
//    }
//
//    private List<Float> getX() {
//        List<Long> dataList = new ArrayList<Long>() {{
//            add(Long.valueOf(1657152635));
//            add(Long.valueOf(1657066235));
//            add(Long.valueOf(1656979835));
//            add(Long.valueOf(1656893435));
//            add(Long.valueOf(1656807035));
//        }};
//        Collections.sort(dataList);
//        List<Float> floats = new ArrayList<>();
//        for (int i = 0; i < dataList.size(); i++) {
//            float seconds = (float) (dataList.get(i) / 1000);
//            floats.add(seconds);
//        }
////        return floats;
////    }
////
//    // makes y values, reduce all y values by a factor of 1000 to get relative values
//    private List<Entry> makeEntries(List<Float> floats) {
//        ArrayList<Entry> values = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            values.add(new Entry(floats.get(i), (float) (Math.random()*i)));
//        }
//        Log.i("entries", values.toString());
//        return values;
//    }
}