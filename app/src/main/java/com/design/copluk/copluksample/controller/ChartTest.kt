package com.design.copluk.copluksample.controller

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.design.copluk.copluksample.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.ArrayList

/**
 * Created by copluk on 2018/5/1.
 */

class ChartTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_test)

        val chart = findViewById<LineChart>(R.id.chart)

        val dataObjects = ArrayList<ChartData>()
        for (i in 0..19) {
            val chartData = ChartData()
            chartData.valueX = i + 10
            chartData.valueY = i + 15

            dataObjects.add(chartData)
        }

        val entries = ArrayList<Entry>()

        for (data in dataObjects) {

            // turn your data into Entry objects
            entries.add(Entry(data.valueX.toFloat(), data.valueY.toFloat()))
        }

        val dataSet = LineDataSet(entries, "Label") // add entries to dataset
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK // styling, ...

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate() // refresh
    }


    internal inner class ChartData {
        var valueX: Int = 0
        var valueY: Int = 0
    }
}