package com.design.copluk.copluksample.controller

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.service.autofill.Dataset
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.design.copluk.copluksample.R
import com.design.copluk.copluksample.controller.ChartActivity.BarValueFormatter
import com.design.copluk.copluksample.controller.ChartActivity.LabelFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import kotlinx.android.synthetic.main.activity_chart_test.*
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.Collections.rotate
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer


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
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
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
        mLeftAxis.setDrawZeroLine(true)
//        mLeftAxis.labelCount = 7
        mLeftAxis.setDrawTopYLabelEntry(true)
//        mLeftAxis.setDrawAxisLine(false)
//        mLeftAxis.axisMinimum = 0f


        val INT_FORMAT = DecimalFormat("###")
        val mXAxis = chart.xAxis
        mXAxis.position = XAxis.XAxisPosition.BOTTOM
        mXAxis.labelCount = 5
        mXAxis.granularity = 1f
        mXAxis.setDrawGridLines(false)
        mXAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            val lastIdx = axis.mEntryCount - 1
            var s = "test"
//            if (value == axis.mEntries[lastIdx]){
//                s = "test"
//            }else{
//                s = INT_FORMAT.format(value.toDouble())
//            }
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
        dataSet.valueTextColor = Color.BLACK // styling, ...
        dataSet.setDrawValues(true)
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
        leftDataSet.valueTextColor = Color.BLACK // styling, ...
        leftDataSet.setDrawValues(true)
        leftDataSet.valueTextSize = 15f


        val lineData = BarData(dataSet)
        lineData.addDataSet(leftDataSet)
        chart.data = lineData
        chart.setScaleEnabled(false) //disable zooming
        chart.setVisibleXRangeMaximum(5f)
//        chart.setFitBars(true)

//        var barChartCustomRenderer = HorizontalBarChartRendererr(chart, chart.animator, chart.viewPortHandler)
//        chart.renderer = barChartCustomRenderer


        chart.invalidate() // refresh
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