package com.design.copluk.copluksample.controller;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.design.copluk.copluksample.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.github.mikephil.charting.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copluk on 2017/6/23.
 */

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_test);

        Button btnBefore = findViewById(R.id.btnBefore);
        Button btnAfter = findViewById(R.id.btnAfter);


        final BarChart chart = findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(true);

        YAxis mLeftAxis = chart.getAxisLeft();
        mLeftAxis.setDrawGridLines(false);
        mLeftAxis.setLabelCount(7);
        mLeftAxis.setDrawTopYLabelEntry(true);
        mLeftAxis.setAxisMinimum(0f);

        XAxis mXAxis = chart.getXAxis();
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mXAxis.setLabelCount(5);
        mXAxis.setGranularity(1f);
        mXAxis.setDrawGridLines(false);


        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setEnabled(true);


        List<ChartData> dataObjects = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i++){
            ChartData chartData = new ChartData();
            chartData.valueX = i ;
            chartData.valueY = i ;

            dataObjects.add(chartData);
        }

        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (ChartData data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new BarEntry(data.valueX, data.valueY));
        }

        final BarDataSet dataSet = new BarDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK); // styling, ...
        dataSet.setDrawValues(false);

        BarData lineData = new BarData(dataSet);
        chart.setData(lineData);
        chart.setScaleEnabled(false); //disable zooming
        chart.setVisibleXRangeMaximum(5f);

        chart.invalidate(); // refresh


        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("getLowestVisibleX" , String.valueOf(chart.getLowestVisibleX()));
                Log.e("getHighestVisibleX" , String.valueOf(chart.getHighestVisibleX()));
                Log.d("test" , String.valueOf(chart.getHighestVisibleX() - 5f));

                chart.moveViewToX(chart.getLowestVisibleX() - 5f);
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("getLowestVisibleX" , String.valueOf(chart.getLowestVisibleX()));
                Log.e("getHighestVisibleX" , String.valueOf(chart.getHighestVisibleX()));

                Log.d("test" , String.valueOf(chart.getLowestVisibleX() + 5f));
                chart.moveViewToX(chart.getLowestVisibleX() + 5f);
            }
        });
    }


    class ChartData{
        public int valueX;
        public int valueY;
    }


    public class LabelFormatter implements IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // assuming that getHeader() returned the label
            return (String) entry.getData();
        }
    }

    public class BarValueFormatter implements IAxisValueFormatter {
        private final DataSet mData;

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return String.valueOf((int) mData.getEntriesForXValue(value).get(0));
        }

        public BarValueFormatter(DataSet data) {
            mData = data;
        }

    }

}
