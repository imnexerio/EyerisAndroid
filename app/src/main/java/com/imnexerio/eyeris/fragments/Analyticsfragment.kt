package com.imnexerio.eyeris.fragments

import androidx.fragment.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imnexerio.eyeris.R
import com.imnexerio.eyeris.helpers.BlinkDatabaseHelper
import com.taosif7.android.ringchartlib.RingChart
import com.taosif7.android.ringchartlib.models.RingChartData


class Analyticsfragment : Fragment() {

    private lateinit var ringChart: RingChart
    private lateinit var databaseHelper: BlinkDatabaseHelper
    private val TAG = "AnalyticsFragment"

    var left_eye_open=0.0f
    var left_eye_closed=0.0f
    var right_eye_open=0.0f
    var right_eye_closed=0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
        ringChart = view.findViewById(R.id.ringChart)
        ringChart.setLayoutMode(RingChart.renderMode.MODE_CONCENTRIC)
        databaseHelper = BlinkDatabaseHelper(requireContext())
//        plotData()
//        getBlinkData()
        return view

    }

    override fun onResume() {
        super.onResume()
        getBlinkData()
    }

    private fun plotData() {

        val dataPoints = mapOf(
            "Left eye open" to left_eye_open,
            "Left eye closed" to left_eye_closed,
            "Right eye open" to right_eye_open,
            "Right eye closed" to right_eye_closed
        )

        // Colors for each segment
        val colors = mapOf(
            "Left eye open" to Color.GREEN,
            "Left eye closed" to Color.YELLOW,
            "Right eye open" to Color.BLUE,
            "Right eye closed" to Color.RED
        )

        // Log the data points for debugging
        Log.i(TAG, "Data Points: $dataPoints")

        // Create RingChartData list
        val ringChartData = dataPoints.map { (label, value) ->
            RingChartData(value, colors[label]!!, label)
        }

        // Log the RingChartData list for debugging
        Log.i(TAG, "RingChartData: $ringChartData")

        // Set data to RingChart
        ringChart.setLayoutMode(RingChart.renderMode.MODE_CONCENTRIC)
        ringChart.setData(ringChartData)
        ringChart.startAnimateLoading()
        ringChart.stopAnimateLoading()

        // Log the completion of the method
        Log.i(TAG, "Data set to RingChart")
    }

    private fun getBlinkData(){
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT timestamp, left_open, left_closed, right_open, right_closed FROM blink_data ORDER BY timestamp DESC LIMIT 5", null)
        if (cursor.moveToFirst()) {
            do {
                val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"))
                val leftOpen = cursor.getInt(cursor.getColumnIndexOrThrow("left_open"))
                val leftClosed = cursor.getInt(cursor.getColumnIndexOrThrow("left_closed"))
                val rightOpen = cursor.getInt(cursor.getColumnIndexOrThrow("right_open"))
                val rightClosed = cursor.getInt(cursor.getColumnIndexOrThrow("right_closed"))

    //            blinkData.add(Pair(timestamp, Pair(Pair(leftOpen, leftClosed), Pair(rightOpen, rightClosed))))
                left_eye_open=leftOpen.toFloat()+left_eye_open
                left_eye_closed=leftClosed.toFloat()+left_eye_closed
                right_eye_open=rightOpen.toFloat()+right_eye_open
                right_eye_closed=rightClosed.toFloat()+right_eye_closed
            } while (cursor.moveToNext())
        }

    //    Log.i(TAG, "cursor data$blinkData")
        Log.i(TAG, "reached getBlinkData")
        cursor.close()
            left_eye_open=left_eye_open/(left_eye_open+left_eye_closed)
            left_eye_closed=1-left_eye_open
            right_eye_open=right_eye_open/(right_eye_open+right_eye_closed)
            right_eye_closed=1-right_eye_open
            plotData()
    }
}
