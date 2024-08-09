package com.imnexerio.eyeris.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imnexerio.eyeris.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.imnexerio.eyeris.helpers.BlinkDatabaseHelper


//class Analyticsfragment : Fragment() {
//
//    private lateinit var ringChart: RingChart
//    private lateinit var databaseHelper: BlinkDatabaseHelper
//    private val TAG = "AnalyticsFragment"
//
//    var left_eye_open=0.0f
//    var left_eye_closed=0.0f
//    var right_eye_open=0.0f
//    var right_eye_closed=0.0f
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        ringChart = view.findViewById(R.id.ringChart)
//        ringChart.setLayoutMode(RingChart.renderMode.MODE_CONCENTRIC)
//        databaseHelper = BlinkDatabaseHelper(requireContext())
////        plotData()
////        getBlinkData()
//        return view
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        getBlinkData()
//    }
//
//    private fun plotData() {
//
//        val dataPoints = mapOf(
//            "Left eye open" to left_eye_open,
//            "Left eye closed" to left_eye_closed,
//            "Right eye open" to right_eye_open,
//            "Right eye closed" to right_eye_closed
//        )
//
//        // Colors for each segment
//        val colors = mapOf(
//            "Left eye open" to Color.GREEN,
//            "Left eye closed" to Color.YELLOW,
//            "Right eye open" to Color.BLUE,
//            "Right eye closed" to Color.RED
//        )
//
//        // Log the data points for debugging
//        Log.i(TAG, "Data Points: $dataPoints")
//
//        // Create RingChartData list
//        val ringChartData = dataPoints.map { (label, value) ->
//            RingChartData(value, colors[label]!!, label)
//        }
//
//        // Log the RingChartData list for debugging
//        Log.i(TAG, "RingChartData: $ringChartData")
//
//        // Set data to RingChart
//        ringChart.setLayoutMode(RingChart.renderMode.MODE_CONCENTRIC)
//        ringChart.setData(ringChartData)
//        ringChart.startAnimateLoading()
//        ringChart.stopAnimateLoading()
//
//        // Log the completion of the method
//        Log.i(TAG, "Data set to RingChart")
//    }
//
//    private fun getBlinkData(){
//        val db = databaseHelper.readableDatabase
//        val cursor = db.rawQuery("SELECT timestamp, left_open, left_closed, right_open, right_closed FROM blink_data ORDER BY timestamp DESC LIMIT 5", null)
//        if (cursor.moveToFirst()) {
//            do {
//                val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"))
//                val leftOpen = cursor.getInt(cursor.getColumnIndexOrThrow("left_open"))
//                val leftClosed = cursor.getInt(cursor.getColumnIndexOrThrow("left_closed"))
//                val rightOpen = cursor.getInt(cursor.getColumnIndexOrThrow("right_open"))
//                val rightClosed = cursor.getInt(cursor.getColumnIndexOrThrow("right_closed"))
//
//    //            blinkData.add(Pair(timestamp, Pair(Pair(leftOpen, leftClosed), Pair(rightOpen, rightClosed))))
//                left_eye_open=leftOpen.toFloat()+left_eye_open
//                left_eye_closed=leftClosed.toFloat()+left_eye_closed
//                right_eye_open=rightOpen.toFloat()+right_eye_open
//                right_eye_closed=rightClosed.toFloat()+right_eye_closed
//            } while (cursor.moveToNext())
//        }
//
//    //    Log.i(TAG, "cursor data$blinkData")
//        Log.i(TAG, "reached getBlinkData")
//        cursor.close()
//            left_eye_open=left_eye_open/(left_eye_open+left_eye_closed)
//            left_eye_closed=1-left_eye_open
//            right_eye_open=right_eye_open/(right_eye_open+right_eye_closed)
//            right_eye_closed=1-right_eye_open
//            plotData()
//    }
//}


//class Analyticsfragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_analytics)
//
//        val anyChartView: AnyChartView = findViewById(R.id.any_chart_view)
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar))
//
//        val circularGauge = AnyChart.circular()
//        circularGauge.data(SingleValueDataSet(arrayOf("23", "34", "67", "93", "56", "100")))
//        circularGauge.fill("#fff")
//            .stroke(null)
//            .padding(0.0, 0.0, 0.0, 0.0)
//            .margin(100.0, 100.0, 100.0, 100.0)
//        circularGauge.startAngle(0.0)
//        circularGauge.sweepAngle(270.0)
//
//        val xAxis = circularGauge.axis(0)
//            .radius(100.0)
//            .width(1.0)
//            .fill(null as Fill?)
//        xAxis.scale()
//            .minimum(0.0)
//            .maximum(100.0)
//        xAxis.ticks("{ interval: 1 }")
//            .minorTicks("{ interval: 1 }")
//        xAxis.labels().enabled(false)
//        xAxis.ticks().enabled(false)
//        xAxis.minorTicks().enabled(false)
//
//        circularGauge.label(0.0)
//            .text("Temazepam, <span style=\"\">32%</span>")
//            .useHtml(true)
//            .hAlign(HAlign.CENTER)
//            .vAlign(VAlign.MIDDLE)
//        circularGauge.label(0.0)
//            .anchor(Anchor.RIGHT_CENTER)
//            .padding(0.0, 10.0, 0.0, 0.0)
//            .height((17.0 / 2.0).toString() + "%")
//            .offsetY(100.0.toString() + "%")
//            .offsetX(0.0)
//        val bar0: Bar = circularGauge.bar(0.0)
//        bar0.dataIndex(0.0)
//        bar0.radius(100.0)
//        bar0.width(17.0)
//        bar0.fill(SolidFill("#64b5f6", 1.0))
//        bar0.stroke(null)
//        bar0.zIndex(5.0)
//        val bar100: Bar = circularGauge.bar(100.0)
//        bar100.dataIndex(5.0)
//        bar100.radius(100.0)
//        bar100.width(17.0)
//        bar100.fill(SolidFill("#F5F4F4", 1.0))
//        bar100.stroke("1 #e5e4e4")
//        bar100.zIndex(4.0)
//
//        circularGauge.label(1.0)
//            .text("Guaifenesin, <span style=\"\">34%</span>")
//            .useHtml(true)
//            .hAlign(HAlign.CENTER)
//            .vAlign(VAlign.MIDDLE)
//        circularGauge.label(1.0)
//            .anchor(Anchor.RIGHT_CENTER)
//            .padding(0.0, 10.0, 0.0, 0.0)
//            .height((17.0 / 2.0).toString() + "%")
//            .offsetY(80.0.toString() + "%")
//            .offsetX(0.0)
//        val bar1: Bar = circularGauge.bar(1.0)
//        bar1.dataIndex(1.0)
//        bar1.radius(80.0)
//        bar1.width(17.0)
//        bar1.fill(SolidFill("#1976d2", 1.0))
//        bar1.stroke(null)
//        bar1.zIndex(5.0)
//        val bar101: Bar = circularGauge.bar(101.0)
//        bar101.dataIndex(5.0)
//        bar101.radius(80.0)
//        bar101.width(17.0)
//        bar101.fill(SolidFill("#F5F4F4", 1.0))
//        bar101.stroke("1 #e5e4e4")
//        bar101.zIndex(4.0)
//
//        circularGauge.label(2.0)
//            .text("Salicylic Acid, <span style=\"\">67%</span>")
//            .useHtml(true)
//            .hAlign(HAlign.CENTER)
//            .vAlign(VAlign.MIDDLE)
//        circularGauge.label(2.0)
//            .anchor(Anchor.RIGHT_CENTER)
//            .padding(0.0, 10.0, 0.0, 0.0)
//            .height((17.0 / 2.0).toString() + "%")
//            .offsetY(60.0.toString() + "%")
//            .offsetX(0.0)
//        val bar2: Bar = circularGauge.bar(2.0)
//        bar2.dataIndex(2.0)
//        bar2.radius(60.0)
//        bar2.width(17.0)
//        bar2.fill(SolidFill("#ef6c00", 1.0))
//        bar2.stroke(null)
//        bar2.zIndex(5.0)
//        val bar102: Bar = circularGauge.bar(102.0)
//        bar102.dataIndex(5.0)
//        bar102.radius(60.0)
//        bar102.width(17.0)
//        bar102.fill(SolidFill("#F5F4F4", 1.0))
//        bar102.stroke("1 #e5e4e4")
//        bar102.zIndex(4.0)
//
//        circularGauge.label(3.0)
//            .text("Fluoride, <span style=\"\">93%</span>")
//            .useHtml(true)
//            .hAlign(HAlign.CENTER)
//            .vAlign(VAlign.MIDDLE)
//        circularGauge.label(3.0)
//            .anchor(Anchor.RIGHT_CENTER)
//            .padding(0.0, 10.0, 0.0, 0.0)
//            .height((17.0 / 2.0).toString() + "%")
//            .offsetY(40.0.toString() + "%")
//            .offsetX(0.0)
//        val bar3: Bar = circularGauge.bar(3.0)
//        bar3.dataIndex(3.0)
//        bar3.radius(40.0)
//        bar3.width(17.0)
//        bar3.fill(SolidFill("#ffd54f", 1.0))
//        bar3.stroke(null)
//        bar3.zIndex(5.0)
//        val bar103: Bar = circularGauge.bar(103.0)
//        bar103.dataIndex(5.0)
//        bar103.radius(40.0)
//        bar103.width(17.0)
//        bar103.fill(SolidFill("#F5F4F4", 1.0))
//        bar103.stroke("1 #e5e4e4")
//        bar103.zIndex(4.0)
//
//        circularGauge.label(4.0)
//            .text("Zinc Oxide, <span style=\"\">56%</span>")
//            .useHtml(true)
//            .hAlign(HAlign.CENTER)
//            .vAlign(VAlign.MIDDLE)
//        circularGauge.label(4.0)
//            .anchor(Anchor.RIGHT_CENTER)
//            .padding(0.0, 10.0, 0.0, 0.0)
//            .height((17.0 / 2.0).toString() + "%")
//            .offsetY(20.0.toString() + "%")
//            .offsetX(0.0)
//        val bar4: Bar = circularGauge.bar(4.0)
//        bar4.dataIndex(4.0)
//        bar4.radius(20.0)
//        bar4.width(17.0)
//        bar4.fill(SolidFill("#455a64", 1.0))
//        bar4.stroke(null)
//        bar4.zIndex(5.0)
//        val bar104: Bar = circularGauge.bar(104.0)
//        bar104.dataIndex(5.0)
//        bar104.radius(20.0)
//        bar104.width(17.0)
//        bar104.fill(SolidFill("#F5F4F4", 1.0))
//        bar104.stroke("1 #e5e4e4")
//        bar104.zIndex(4.0)
//
//        circularGauge.margin(50.0, 50.0, 50.0, 50.0)
//        circularGauge.title()
//            .text(
//                """Medicine manufacturing progress' +
//    '<br/><span style="color:#929292; font-size: 12px;">(ACME CORPORATION)</span>"""
//            )
//            .useHtml(true)
//        circularGauge.title().enabled(true)
//        circularGauge.title().hAlign(HAlign.CENTER)
//        circularGauge.title()
//            .padding(0.0, 0.0, 0.0, 0.0)
//            .margin(0.0, 0.0, 20.0, 0.0)
//
//        anyChartView.setChart(circularGauge)
//    }
//}




//class Analyticsfragment : Fragment() {
//
//    private lateinit var aaChartView: AAChartView
//    private lateinit var databaseHelper: BlinkDatabaseHelper
//    private val TAG = "AnalyticsFragment"
//
//    var left_eye_open=0.0f
//    var left_eye_closed=0.0f
//    var right_eye_open=0.0f
//    var right_eye_closed=0.0f
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        aaChartView = view.findViewById(R.id.aa_chart_view)
//
//        val aaChartModel = AAChartModel()
//            .chartType(AAChartType.Gauge)
//            .title("Average Blink Rate")
//            .subtitle("Average blink rate per minute")
////            .backgroundColor("#ffffff")
//
//
//        aaChartView.aa_drawChartWithChartModel(aaChartModel)
//
//        return view
//    }
//
//    private fun getBlinkData(){
//        val db = databaseHelper.readableDatabase
//        val cursor = db.rawQuery("SELECT timestamp, left_open, left_closed, right_open, right_closed FROM blink_data ORDER BY timestamp DESC LIMIT 5", null)
//        if (cursor.moveToFirst()) {
//            do {
//                val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"))
//                val leftOpen = cursor.getInt(cursor.getColumnIndexOrThrow("left_open"))
//                val leftClosed = cursor.getInt(cursor.getColumnIndexOrThrow("left_closed"))
//                val rightOpen = cursor.getInt(cursor.getColumnIndexOrThrow("right_open"))
//                val rightClosed = cursor.getInt(cursor.getColumnIndexOrThrow("right_closed"))
//
//    //            blinkData.add(Pair(timestamp, Pair(Pair(leftOpen, leftClosed), Pair(rightOpen, rightClosed))))
//                left_eye_open=leftOpen.toFloat()+left_eye_open
//                left_eye_closed=leftClosed.toFloat()+left_eye_closed
//                right_eye_open=rightOpen.toFloat()+right_eye_open
//                right_eye_closed=rightClosed.toFloat()+right_eye_closed
//            } while (cursor.moveToNext())
//        }
//
//    //    Log.i(TAG, "cursor data$blinkData")
//        Log.i(TAG, "reached getBlinkData")
//        cursor.close()
//            left_eye_open=left_eye_open/(left_eye_open+left_eye_closed)
//            left_eye_closed=1-left_eye_open
//            right_eye_open=right_eye_open/(right_eye_open+right_eye_closed)
//            right_eye_closed=1-right_eye_open
////            plotData()
//    }
//}



class Analyticsfragment : Fragment() {

    private lateinit var aaChartView: AAChartView
    private lateinit var databaseHelper: BlinkDatabaseHelper
    private val TAG = "AnalyticsFragment"

    var left_eye_open = 0.0f
    var left_eye_closed = 0.0f
    var right_eye_open = 0.0f
    var right_eye_closed = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
        aaChartView = view.findViewById(R.id.aa_chart_view)
        databaseHelper = BlinkDatabaseHelper(requireContext())

        getavgBlinkData()

        return view
    }

    private fun getavgBlinkData() {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT timestamp, left_open, left_closed, right_open, right_closed FROM blink_data ORDER BY timestamp DESC LIMIT 5", null)
        if (cursor.moveToFirst()) {
            do {
                val leftOpen = cursor.getInt(cursor.getColumnIndexOrThrow("left_open"))
                val leftClosed = cursor.getInt(cursor.getColumnIndexOrThrow("left_closed"))
                val rightOpen = cursor.getInt(cursor.getColumnIndexOrThrow("right_open"))
                val rightClosed = cursor.getInt(cursor.getColumnIndexOrThrow("right_closed"))

                left_eye_open += leftOpen.toFloat()
                left_eye_closed += leftClosed.toFloat()
                right_eye_open += rightOpen.toFloat()
                right_eye_closed += rightClosed.toFloat()
            } while (cursor.moveToNext())
        }
        cursor.close()

        left_eye_open = if (left_eye_open + left_eye_closed != 0.0f) left_eye_open / (left_eye_open + left_eye_closed) else 0.0f
        left_eye_closed = 1 - left_eye_open
        right_eye_open = if (right_eye_open + right_eye_closed != 0.0f) right_eye_open / (right_eye_open + right_eye_closed) else 0.0f
        right_eye_closed = 1 - right_eye_open


        plotavghData()

    }

    private fun plotavghData() {
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("Blink Data of last 5 minutes")
            .subtitle("Average of ${(left_eye_closed + right_eye_closed) * 100} blink per minute")
    //        .backgroundColor("#ffffff")
    //        .dataLabelsEnabled(false)  // Disable data labels
            .series(arrayOf(
                AASeriesElement()
                    .name("Blink Data")
                    .data(arrayOf(
                        arrayOf("Left Eye Open", left_eye_open.toDouble()),
                        arrayOf("Left Eye Closed", left_eye_closed.toDouble()),
                        arrayOf("Right Eye Open", right_eye_open.toDouble()),
                        arrayOf("Right Eye Closed", right_eye_closed.toDouble())
                    ))
                    .dataLabels(
                        AADataLabels()
                        .enabled(false)  // Disable data labels for each slice
                    )
            ))

            aaChartView.aa_drawChartWithChartModel(aaChartModel)
            aaChartView.isClearBackgroundColor = true
        }

}