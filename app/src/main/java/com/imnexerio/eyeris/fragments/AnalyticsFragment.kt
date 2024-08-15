package com.imnexerio.eyeris.fragments

import android.os.Bundle
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



class AnalyticsFragment : Fragment() {

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