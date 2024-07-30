package com.imnexerio.eyeris.views


import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.charts.LineChart

//class RealtimePlotView @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
//) : LineChart(context, attrs, defStyleAttr) {
//
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//    private val MAX_ENTRIES = 30
//
//    private val TAG = "RealtimePlotView"
//
//    init {
//        setupChart()
//    }
//
//
//
//    private fun setupChart() {
//        val textColor = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.WHITE)
//        this.description.isEnabled = false
//        this.setTouchEnabled(false)
//        this.isDragEnabled = false
//        this.setScaleEnabled(false)
//        this.setDrawGridBackground(false)
//        this.setPinchZoom(false)
//        this.setBackgroundColor(Color.TRANSPARENT)
//
//        val data = LineData()
//        data.setValueTextColor(textColor)
//        this.data = data
//
//        val l = this.legend
//        l.form = Legend.LegendForm.LINE
//        l.textColor = textColor
//
//        val xl = this.xAxis
//        xl.textColor = textColor
//        xl.setDrawGridLines(true)
//        xl.setAvoidFirstLastClipping(true)
//        xl.isEnabled = false
//
//        val leftAxis = this.axisLeft
//        leftAxis.textColor = textColor
//        leftAxis.setDrawGridLines(false)
//        leftAxis.axisMaximum = 10f
//        leftAxis.axisMinimum = -1f
//        leftAxis.setDrawGridLines(true)
//
//        val rightAxis = this.axisRight
//        rightAxis.isEnabled = false
//
//        this.axisLeft.setDrawGridLines(false)
//        this.xAxis.setDrawGridLines(false)
//        this.setDrawBorders(false)
//
//        // Initialize DataSets
//        leftBlinkDataSet = createSet("Left Blink Score", Color.MAGENTA)
//        rightBlinkDataSet = createSet("Right Blink Score", Color.CYAN)
//        data.addDataSet(leftBlinkDataSet)
//        data.addDataSet(rightBlinkDataSet)
//        addInitialEntry()
//    }
//
//    private fun addInitialEntry() {
//        addEntry(0.1f, 0.1f)
//    }
//
//    fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
////        Log.i(TAG, "Left: $leftBlinkScore, Right: $rightBlinkScore")
//        val data = this.data
//        if (data != null) {
////            if (leftBlinkDataSet.entryCount >= MAX_ENTRIES) {
////                leftBlinkDataSet.removeFirst()
////                adjustXValues(leftBlinkDataSet)
////            }
////            if (rightBlinkDataSet.entryCount >= MAX_ENTRIES) {
////                rightBlinkDataSet.removeFirst()
////                adjustXValues(rightBlinkDataSet)
////            }
//
//            data.addEntry(Entry(leftBlinkDataSet.`entryCount`.toFloat(), leftBlinkScore * 10), 0)
//            data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
//            data.notifyDataChanged()
//            this.notifyDataSetChanged()
//            this.setVisibleXRangeMaximum(25f)
//            this.moveViewToX(data.entryCount.toFloat())
//        }
//    }
//
////    private fun adjustXValues(dataSet: LineDataSet) {
////        for (i in 0 until dataSet.entryCount) {
////            dataSet.getEntryForIndex(i).x = i.toFloat()
////        }
////    }
//
//    private fun createSet(label: String, color: Int): LineDataSet {
//        val set = LineDataSet(null, label)
//        set.axisDependency = YAxis.AxisDependency.LEFT
//        set.lineWidth = 3f
//        set.color = color
//        set.isHighlightEnabled = false
//        set.setDrawValues(false)
//        set.setDrawCircles(false)
//        set.mode = LineDataSet.Mode.CUBIC_BEZIER
//        set.cubicIntensity = 0.2f
//        return set
//    }
//}

class RealtimePlotView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LineChart(context, attrs, defStyleAttr) {

    private lateinit var leftBlinkDataSet: LineDataSet
    private lateinit var rightBlinkDataSet: LineDataSet
    private val MAX_ENTRIES = 100

    private val updateHandler = Handler(Looper.getMainLooper())
    private val updateInterval = 10L // 100 milliseconds or adjust as needed

    private var pendingLeftBlinkScore: Float? = null
    private var pendingRightBlinkScore: Float? = null

    init {
        setupChart()
    }

    private fun setupChart() {
        val textColor = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.WHITE)
        this.description.isEnabled = false
        this.setTouchEnabled(false)
        this.isDragEnabled = false
        this.setScaleEnabled(false)
        this.setDrawGridBackground(false)
        this.setPinchZoom(false)
        this.setBackgroundColor(Color.TRANSPARENT)

        val data = LineData()
        data.setValueTextColor(textColor)
        this.data = data

        val l = this.legend
        l.form = Legend.LegendForm.LINE
        l.textColor = textColor

        val xl = this.xAxis
        xl.textColor = textColor
        xl.setDrawGridLines(true)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = false

        val leftAxis = this.axisLeft
        leftAxis.textColor = textColor
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMaximum = 10f
        leftAxis.axisMinimum = -1f
        leftAxis.setDrawGridLines(true)

        val rightAxis = this.axisRight
        rightAxis.isEnabled = false

        this.axisLeft.setDrawGridLines(false)
        this.xAxis.setDrawGridLines(false)
        this.setDrawBorders(false)

        // Initialize DataSets
        leftBlinkDataSet = createSet("Left Blink Score", Color.MAGENTA)
        rightBlinkDataSet = createSet("Right Blink Score", Color.CYAN)
        data.addDataSet(leftBlinkDataSet)
        data.addDataSet(rightBlinkDataSet)
    }

    fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
        pendingLeftBlinkScore = leftBlinkScore
        pendingRightBlinkScore = rightBlinkScore
        updateHandler.postDelayed(updateRunnable, updateInterval)
    }

    private val updateRunnable = Runnable {
        pendingLeftBlinkScore?.let { leftScore ->
            pendingRightBlinkScore?.let { rightScore ->
                val data = this.data
                if (data != null) {
                    if (leftBlinkDataSet.entryCount >= MAX_ENTRIES) {
                        leftBlinkDataSet.removeFirst()
                        adjustXValues(leftBlinkDataSet)
                    }
                    if (rightBlinkDataSet.entryCount >= MAX_ENTRIES) {
                        rightBlinkDataSet.removeFirst()
                        adjustXValues(rightBlinkDataSet)
                    }

                    data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftScore * 10), 0)
                    data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightScore * 10), 1)
                    data.notifyDataChanged()
                    this.notifyDataSetChanged()
                    this.setVisibleXRangeMaximum(25f)
                    this.moveViewToX(data.entryCount.toFloat())
                }
            }
        }
    }

    private fun adjustXValues(dataSet: LineDataSet) {
        for (i in 0 until dataSet.entryCount) {
            dataSet.getEntryForIndex(i).x = i.toFloat()
        }
    }

    private fun createSet(label: String, color: Int): LineDataSet {
        val set = LineDataSet(null, label)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.lineWidth = 3f
        set.color = color
        set.isHighlightEnabled = false
        set.setDrawValues(false)
        set.setDrawCircles(false)
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.cubicIntensity = 0.2f
        return set
    }
}
