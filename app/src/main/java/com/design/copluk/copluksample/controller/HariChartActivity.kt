package com.design.copluk.copluksample.controller

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.design.copluk.copluksample.R
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlinx.android.synthetic.main.activity_chart_test.*
import java.text.DecimalFormat
import java.util.*


/**
 * Created by copluk on 2018/5/10.
 */

class HariChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hari_chart_test)

//        val btnBefore = findViewById<Button>(R.id.btnBefore)
//        val btnAfter = findViewById<Button>(R.id.btnAfter)


        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)
        chart.isHighlightFullBarEnabled = true
//        chart.setDrawValueAboveBar(false)

        val l = chart.legend
        l.isWordWrapEnabled = true
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(true)
        l.isEnabled = true

        btnBefore.setOnClickListener {
            //            Log.e("getLowestVisibleX", chart.lowestVisibleX.toString())
//            Log.e("getHighestVisibleX", chart.highestVisibleX.toString())
//            Log.d("test", (chart.highestVisibleX - 5f).toString())

            chart.moveViewToX(chart.lowestVisibleX - 5f)
        }

        btnAfter.setOnClickListener {
            //            Log.e("getLowestVisibleX", chart.lowestVisibleX.toString())
//            Log.e("getHighestVisibleX", chart.highestVisibleX.toString())
//
//            Log.d("test", (chart.lowestVisibleX + 5f).toString())
            chart.moveViewToX(chart.lowestVisibleX + 5f)
        }


        val mLeftAxis = chart.axisLeft
        mLeftAxis.setDrawGridLines(false)
//        mLeftAxis.setDrawZeroLine(true)
//        mLeftAxis.setDrawTopYLabelEntry(true)

        val INT_FORMAT = DecimalFormat("###")
        val mXAxis = chart.xAxis
        mXAxis.position = XAxis.XAxisPosition.BOTTOM
        mXAxis.labelCount = 5
        mXAxis.granularity = 1f
        mXAxis.setDrawGridLines(false)
        mXAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            val lastIdx = axis.mEntryCount - 1
            var s = "test"
            return@IAxisValueFormatter s
        }

        loadData()


    }

    private fun loadData() {
        val dataObjects = ArrayList<ChartData>()
        for (i in 0..19) {
            val chartData = ChartData()
            chartData.valueX = i
            chartData.valueY = i

            dataObjects.add(chartData)
        }


        val entries = dataObjects.map {
            // turn your data into Entry objects
            BarEntry(it.valueX.toFloat(), it.valueY.toFloat())
        }

        val dataSet = BarDataSet(entries, "Label") // add entries to dataset
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.TRANSPARENT // styling, ...
//        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 15f

        val leftDataObjects = ArrayList<ChartData>()
        for (i in 0..19) {
            val chartData = ChartData()
            chartData.valueX = i
            chartData.valueY = i

            leftDataObjects.add(chartData)
        }

        val leftEntries = leftDataObjects.map { (BarEntry(it.valueX.toFloat(), -it.valueY.toFloat())) }


        val leftDataSet = BarDataSet(leftEntries, "leftLabel") // add entries to dataset
        leftDataSet.color = Color.RED
        leftDataSet.valueTextColor = Color.TRANSPARENT // styling, ...
//        leftDataSet.setDrawValues(true)
        leftDataSet.valueTextSize = 15f


        val valueDataObjects = ArrayList<ChartData>()
        for (i in 0..19) {
            val chartData = ChartData()
            chartData.valueX = i
            chartData.valueY = i

            valueDataObjects.add(chartData)
        }

        val valueEntries = valueDataObjects.map {
            BarEntry(it.valueX.toFloat(), 0.1f)
        }
        var j = 0
        valueEntries.forEach{ it.data = j
            j++}
        val valueDataSet = BarDataSet(valueEntries, "Label") // add entries to dataset
        valueDataSet.valueTextColor = Color.BLACK // styling, ...
        valueDataSet.color = Color.TRANSPARENT
        valueDataSet.setDrawValues(true)
        valueDataSet.valueTextSize = 15f
        valueDataSet.valueFormatter = mTargetRateFormatter

        val valueLeftDataObjects = ArrayList<ChartData>()
        for (i in 0..19) {
            val chartData = ChartData()
            chartData.valueX = i
            chartData.valueY = i

            valueLeftDataObjects.add(chartData)
        }

        val valueLeftEntries = valueLeftDataObjects.map {
           BarEntry(it.valueX.toFloat(), -0.1f)
        }
        var i = 0
        valueLeftEntries.forEach{ it.data = i
        i++}
        val valueLeftDataSet = BarDataSet(valueLeftEntries, "Label") // add entries to dataset
        valueLeftDataSet.valueTextColor = Color.BLACK // styling, ...
        valueLeftDataSet.color = Color.TRANSPARENT
        valueLeftDataSet.setDrawValues(true)
        valueLeftDataSet.valueTextSize = 15f
        valueLeftDataSet.valueFormatter = mTargetRateFormatter




        val lineData = BarData(dataSet)
        lineData.addDataSet(leftDataSet)
        lineData.addDataSet(valueDataSet)
        lineData.addDataSet(valueLeftDataSet)
        chart.data = lineData
        chart.setScaleEnabled(false) //disable zooming
        chart.setVisibleXRangeMaximum(5f)



        chart.invalidate() // refresh
    }

    var mTargetRateFormatter: IValueFormatter = object : IValueFormatter {
        override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
            return entry.data.toString()
        }
    }


    internal inner class ChartData {
        var valueX: Int = 0
        var valueY: Int = 0
    }

    inner class HorizontalBarChartRendererr(chart: BarDataProvider, animator: ChartAnimator, viewPortHandler: ViewPortHandler) : HorizontalBarChartRenderer(chart, animator, viewPortHandler) {

        override fun drawValue(c: Canvas, formatter: IValueFormatter, value: Float, entry: Entry, dataSetIndex: Int, x: Float, y: Float, color: Int) {
            mValuePaint.color = color
            c.save()

            Log.e("test", "here is run")
            Log.e("test", "X : $color")

            if (value < 0) {
                c.rotate(90f, x, y)
                c.drawText(formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler), x, y, mValuePaint)
            }

            if (color == Color.BLUE) {
                Log.e("test", "X : $x")
                Log.e("test", "Y : $y")
                Log.e("test", "Value : $value")
            } else {
                Log.d("test", "R X : $x")
                Log.d("test", "R Y : $y")
                Log.d("test", "R Value : $value")
            }


            c.restore()
        }
    }


}