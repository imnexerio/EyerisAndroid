package com.imnexerio.eyeris.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.fragment.app.Fragment
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.imnexerio.eyeris.R



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
//    private lateinit var lineChart: LineChart
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//    private lateinit var lineData: LineData
//    private lateinit var chartLifecycleObserver: ChartLifecycleObserver
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//
//        lineChart = view.findViewById(R.id.realtimelineChart)
//
//        // Initialize DataSets
//        leftBlinkDataSet = LineDataSet(ArrayList(), "Left Blink Score")
//        rightBlinkDataSet = LineDataSet(ArrayList(), "Right Blink Score")
//        Log.i("AnalyticsFragment", "DataSets initialized")
//        Log.i("AnalyticsFragment", "Left Blink DataSet: $leftBlinkDataSet")
//        Log.i("AnalyticsFragment", "Right Blink DataSet: $rightBlinkDataSet")
//        // Customize DataSets
//        leftBlinkDataSet.color = Color.BLUE
//        rightBlinkDataSet.color = Color.RED
//
//        // Initialize LineData with the DataSets
//        lineData = LineData(leftBlinkDataSet, rightBlinkDataSet)
//        Log.i("AnalyticsFragment", "LineData initialized")
//        Log.i("AnalyticsFragment", "LineData: $lineData")
//
//        // Set data to the chart
//        lineChart.data = lineData
//
//        // Customize the chart (optional)
//        lineChart.description.isEnabled = false
//        lineChart.setTouchEnabled(true)
//        lineChart.isDragEnabled = true
//        lineChart.setScaleEnabled(true)
//        lineChart.setPinchZoom(true)
//
//        // Initialize and register the LifecycleObserver
//        chartLifecycleObserver = ChartLifecycleObserver { leftBlinkScore, rightBlinkScore ->
//            updateChart(leftBlinkScore, rightBlinkScore)
//        }
//        lifecycle.addObserver(chartLifecycleObserver)
//
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        lifecycle.removeObserver(chartLifecycleObserver)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.i("AnalyticsFragment", "onResume called")
//        lifecycle.addObserver(chartLifecycleObserver)
//    }
//
//    private fun updateChart(leftBlinkScore: Float, rightBlinkScore: Float) {
//        val leftEntry = Entry(System.currentTimeMillis().toFloat(), leftBlinkScore)
//        val rightEntry = Entry(System.currentTimeMillis().toFloat(), rightBlinkScore)
//
//        leftBlinkDataSet.addEntry(leftEntry)
//        rightBlinkDataSet.addEntry(rightEntry)
//        Log.i("AnalyticsFragment", "Entries added to the DataSets")
//        Log.i("AnalyticsFragment", "Left Blink Score: $leftBlinkScore")
//        Log.i("AnalyticsFragment", "Right Blink Score: $rightBlinkScore")
//        lineData.notifyDataChanged()
//        lineChart.notifyDataSetChanged()
//        lineChart.invalidate() // Refresh the chart
//    }
//}

//class Analyticsfragment : Fragment(), DataUpdateListener {
//
//    private lateinit var lineChart: LineChart
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//    private lateinit var lineData: LineData
//    private var service: FaceLandmarkerService? = null
//    private var serviceConnection: ServiceConnection? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//
//        lineChart = view.findViewById(R.id.realtimelineChart)
//
//        // Initialize DataSets
//        leftBlinkDataSet = LineDataSet(ArrayList(), "Left Blink Score")
//        rightBlinkDataSet = LineDataSet(ArrayList(), "Right Blink Score")
//
//        // Customize DataSets
//        leftBlinkDataSet.color = Color.BLUE
//        rightBlinkDataSet.color = Color.RED
//
//        // Initialize LineData with the DataSets
//        lineData = LineData(leftBlinkDataSet, rightBlinkDataSet)
//
//        // Set data to the chart
//        lineChart.data = lineData
//
//        // Customize the chart (optional)
//        lineChart.description.isEnabled = false
//        lineChart.setTouchEnabled(true)
//        lineChart.isDragEnabled = true
//        lineChart.setScaleEnabled(true)
//        lineChart.setPinchZoom(true)
//
//        return view
//    }
//
//    override fun onStart() {
//        super.onStart()
//        // Bind to the service
//        val intent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        serviceConnection = object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
//                service = (binder as? FaceLandmarkerService.LocalBinder)?.getService()
//                service?.setDataUpdateListener(this@Analyticsfragment)
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//                service = null
//            }
//        }
//        requireContext().bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        // Unbind from the service
//        service?.setDataUpdateListener(null)
//        serviceConnection?.let { requireContext().unbindService(it) }
//    }
//
//    override fun onDataUpdate(leftBlinkScore: Float, rightBlinkScore: Float) {
//        // Update your chart here
//        updateChart(leftBlinkScore, rightBlinkScore)
//    }
//
//    private fun updateChart(leftBlinkScore: Float, rightBlinkScore: Float) {
//        val currentTime = System.currentTimeMillis().toFloat()
//        val leftEntry = Entry(currentTime, leftBlinkScore)
//        val rightEntry = Entry(currentTime, rightBlinkScore)
//
//        Log.i("AnalyticsFragment", "Entries added to the DataSets")
//        Log.i("AnalyticsFragment", "Left Blink Score: $leftBlinkScore")
//        Log.i("AnalyticsFragment", "Right Blink Score: $rightBlinkScore")
//
//        leftBlinkDataSet.addEntry(leftEntry)
//        rightBlinkDataSet.addEntry(rightEntry)
//
//        lineData.notifyDataChanged()
//        lineChart.notifyDataSetChanged()
//        lineChart.invalidate() // Refresh the chart
//    }
//}

//
//class Analyticsfragment : Fragment(), DataUpdateListener {
//
//    private lateinit var lineChart: LineChart
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//    private lateinit var lineData: LineData
//    private var service: FaceLandmarkerService? = null
//    private var serviceConnection: ServiceConnection? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//
//        lineChart = view.findViewById(R.id.realtimelineChart)
//
//        // Initialize DataSets
//        leftBlinkDataSet = LineDataSet(ArrayList(), "Left Blink Score")
//        rightBlinkDataSet = LineDataSet(ArrayList(), "Right Blink Score")
//
//        // Customize DataSets
//        leftBlinkDataSet.color = Color.BLUE
//        rightBlinkDataSet.color = Color.RED
//
//        // Initialize LineData with the DataSets
//        lineData = LineData(leftBlinkDataSet, rightBlinkDataSet)
//
//        // Set data to the chart
//        lineChart.data = lineData
//
//        // Customize the chart (optional)
//        lineChart.description.isEnabled = false
//        lineChart.setTouchEnabled(true)
//        lineChart.isDragEnabled = true
//        lineChart.setScaleEnabled(true)
//        lineChart.setPinchZoom(true)
//
//        return view
//    }
//
//    override fun onStart() {
//        super.onStart()
//        // Bind to the service
//        val intent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        serviceConnection = object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
//                service = (binder as? FaceLandmarkerService.LocalBinder)?.getService()
//                service?.setDataUpdateListener(this@Analyticsfragment)
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//                service = null
//            }
//        }
//        requireContext().bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        // Unbind from the service
//        service?.setDataUpdateListener(null)
//        serviceConnection?.let { requireContext().unbindService(it) }
//    }
//
//    override fun onDataUpdate(leftBlinkScore: Float, rightBlinkScore: Float) {
//        // Update your chart here
//        updateChart(leftBlinkScore, rightBlinkScore)
//    }
//
//    private fun updateChart(leftBlinkScore: Float, rightBlinkScore: Float) {
//        val currentTime = System.currentTimeMillis().toFloat()
//        val leftEntry = Entry(currentTime, leftBlinkScore)
//        val rightEntry = Entry(currentTime, rightBlinkScore)
//
//        Log.i("AnalyticsFragment", "Entries added to the DataSets")
//        Log.i("AnalyticsFragment", "Left Blink Score: $leftBlinkScore")
//        Log.i("AnalyticsFragment", "Right Blink Score: $rightBlinkScore")
//
//        leftBlinkDataSet.addEntry(leftEntry)
//        rightBlinkDataSet.addEntry(rightEntry)
//
//        lineData.notifyDataChanged()
//        lineChart.notifyDataSetChanged()
//        lineChart.invalidate() // Refresh the chart
//    }
//}


import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.imnexerio.eyeris.helpers.PlotManager
import com.imnexerio.eyeris.views.RealtimePlotView


//class Analyticsfragment : Fragment(), DataUpdateListener, SensorEventListener {
//
//    private var mSensorManager: SensorManager? = null
//    private var mAccelerometer: Sensor? = null
//    private var mChart: LineChart? = null
//    private var thread: Thread? = null
//    private var plotData = true
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        mSensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
//
//        val sensors = mSensorManager!!.getSensorList(Sensor.TYPE_ALL)
//        for (i in sensors.indices) {
//            Log.d(TAG, "onCreate: Sensor $i: ${sensors[i]}")
//        }
//
//        if (mAccelerometer != null) {
//            mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
//        }
//
//        mChart = view.findViewById(R.id.realtimelineChart)
//        mChart!!.description.isEnabled = true
//        mChart!!.setTouchEnabled(true)
//        mChart!!.isDragEnabled = true
//        mChart!!.setScaleEnabled(true)
//        mChart!!.setDrawGridBackground(false)
//        mChart!!.setPinchZoom(true)
//        mChart!!.setBackgroundColor(Color.WHITE)
//
//        val data = LineData()
//        data.setValueTextColor(Color.WHITE)
//        mChart!!.data = data
//
//        val l = mChart!!.legend
//        l.form = Legend.LegendForm.LINE
//        l.textColor = Color.WHITE
//
//        val xl = mChart!!.xAxis
//        xl.textColor = Color.WHITE
//        xl.setDrawGridLines(true)
//        xl.setAvoidFirstLastClipping(true)
//        xl.isEnabled = true
//
//        val leftAxis = mChart!!.axisLeft
//        leftAxis.textColor = Color.WHITE
//        leftAxis.setDrawGridLines(false)
//        leftAxis.axisMaximum = 10f
//        leftAxis.axisMinimum = 0f
//        leftAxis.setDrawGridLines(true)
//
//        val rightAxis = mChart!!.axisRight
//        rightAxis.isEnabled = false
//
//        mChart!!.axisLeft.setDrawGridLines(false)
//        mChart!!.xAxis.setDrawGridLines(false)
//        mChart!!.setDrawBorders(false)
//
//        feedMultiple()
//        return view
//    }
//
//    private fun addEntry(event: SensorEvent) {
//        val data = mChart!!.data
//        if (data != null) {
//            var set = data.getDataSetByIndex(0)
//            if (set == null) {
//                set = createSet()
//                data.addDataSet(set)
//            }
//            data.addEntry(Entry(set.entryCount.toFloat(), event.values[0] + 5), 0)
//            data.notifyDataChanged()
//            mChart!!.notifyDataSetChanged()
//            mChart!!.setVisibleXRangeMaximum(150f)
//            mChart!!.moveViewToX(data.entryCount.toFloat())
//        }
//    }
//
//    private fun createSet(): LineDataSet {
//        val set = LineDataSet(null, "Dynamic Data")
//        set.axisDependency = YAxis.AxisDependency.LEFT
//        set.lineWidth = 3f
//        set.color = Color.MAGENTA
//        set.isHighlightEnabled = false
//        set.setDrawValues(false)
//        set.setDrawCircles(false)
//        set.mode = LineDataSet.Mode.CUBIC_BEZIER
//        set.cubicIntensity = 0.2f
//        return set
//    }
//
//    private fun feedMultiple() {
//        if (thread != null) {
//            thread!!.interrupt()
//        }
//        thread = Thread {
//            while (true) {
//                plotData = true
//                try {
//                    Thread.sleep(10)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        thread!!.start()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (thread != null) {
//            thread!!.interrupt()
//        }
//        mSensorManager!!.unregisterListener(this)
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
//        // Do something here if sensor accuracy changes.
//    }
//
//    override fun onSensorChanged(event: SensorEvent) {
//        if (plotData) {
//            addEntry(event)
//            plotData = false
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
//    }
//
//    override fun onDestroy() {
//        mSensorManager!!.unregisterListener(this@Analyticsfragment)
//        thread!!.interrupt()
//        super.onDestroy()
//    }
//
//    override fun onDataUpdate(leftBlinkScore: Float, rightBlinkScore: Float) {
//        // Implement the method to update the chart with blink scores
//    }
//
//    companion object {
//        private const val TAG = "AnalyticsFragment"
//    }
//}


//class Analyticsfragment : Fragment(), DataUpdateListener {
//
//    private var mChart: LineChart? = null
//    private var plotData = true
//    private var serviceConnection: ServiceConnection? = null
//    private var service: FaceLandmarkerService? = null
//
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//
//    private val MAX_ENTRIES = 30 // Define the maximum number of entries to keep
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        val textColor = requireContext().theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.WHITE)
//
//        mChart = view.findViewById(R.id.realtimelineChart)
//        mChart!!.description.isEnabled = false
//        mChart!!.setTouchEnabled(false)
//        mChart!!.isDragEnabled = false
//        mChart!!.setScaleEnabled(false)
//        mChart!!.setDrawGridBackground(false)
//        mChart!!.setPinchZoom(false)
//        mChart!!.setBackgroundColor(Color.TRANSPARENT)
//
//        val data = LineData()
////        data.setValueTextColor(Color.WHITE)
//        data.setValueTextColor(textColor)
//        mChart!!.data = data
//
//        val l = mChart!!.legend
//        l.form = Legend.LegendForm.LINE
//        l.textColor = textColor
//
//        val xl = mChart!!.xAxis
//        xl.textColor = textColor
//        xl.setDrawGridLines(true)
//        xl.setAvoidFirstLastClipping(true)
//        xl.isEnabled = false
//
//        val leftAxis = mChart!!.axisLeft
//        leftAxis.textColor = textColor
//        leftAxis.setDrawGridLines(false)
//        leftAxis.axisMaximum = 10f
//        leftAxis.axisMinimum = -1f
//        leftAxis.setDrawGridLines(true)
//
//        val rightAxis = mChart!!.axisRight
//        rightAxis.isEnabled = false
//
//        mChart!!.axisLeft.setDrawGridLines(false)
//        mChart!!.xAxis.setDrawGridLines(false)
//        mChart!!.setDrawBorders(false)
//
//        // Initialize DataSets
//        leftBlinkDataSet = createSet("Left Blink Score", Color.MAGENTA)
//        rightBlinkDataSet = createSet("Right Blink Score", Color.CYAN)
//        data.addDataSet(leftBlinkDataSet)
//        data.addDataSet(rightBlinkDataSet)
//        addInitialEntry()
//
//        return view
//    }
//    private fun addInitialEntry() {
//        addEntry(0.1f, 0.1f)
//    }
//
////    private fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
////        val data = mChart!!.data
////        if (data != null) {
////
////            if (leftBlinkDataSet.entryCount > MAX_ENTRIES) {
////                leftBlinkDataSet.removeEntry(0)
////                leftBlinkDataSet.notifyDataSetChanged()
////            }
////            if (rightBlinkDataSet.entryCount > MAX_ENTRIES) {
////                rightBlinkDataSet.removeEntry(0)
////                rightBlinkDataSet.notifyDataSetChanged()
////            }
////            Log.i("AnalyticsFragment", "Data set: $leftBlinkDataSet")
////            Log.i("AnalyticsFragment", "entry count: ${leftBlinkDataSet.entryCount}")
////
////            data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftBlinkScore * 10), 0)
////            data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
////            data.notifyDataChanged()
////            mChart!!.notifyDataSetChanged()
////            mChart!!.setVisibleXRangeMaximum(25f)
////            mChart!!.moveViewToX(data.entryCount.toFloat())
////        }
////    }
//
////    private fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
////    val data = mChart!!.data
////    if (data != null) {
////        if (leftBlinkDataSet.entryCount > MAX_ENTRIES) {
////            leftBlinkDataSet.removeEntry(0)
////            adjustXValues(leftBlinkDataSet)
//////            leftBlinkDataSet.notifyDataSetChanged()
////        }
////        if (rightBlinkDataSet.entryCount > MAX_ENTRIES) {
////            rightBlinkDataSet.removeEntry(0)
////            adjustXValues(rightBlinkDataSet)
//////            rightBlinkDataSet.notifyDataSetChanged()
////        }
////        Log.i("AnalyticsFragment", "Data set: $leftBlinkDataSet")
////        Log.i("AnalyticsFragment", "entry count: ${leftBlinkDataSet.entryCount}")
////
////        data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftBlinkScore * 10), 0)
////        data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
//////        data.notifyDataChanged()
////        mChart!!.notifyDataSetChanged()
////        mChart!!.setVisibleXRangeMaximum(25f)
////        mChart!!.moveViewToX(data.entryCount.toFloat())
////    }
////}
////
////private fun adjustXValues(dataSet: LineDataSet) {
////    for (i in 0 until dataSet.entryCount) {
////        dataSet.getEntryForIndex(i).x = i.toFloat()
////    }
////}
//
//    private fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
//    val data = mChart!!.data
//    if (data != null) {
//        if (leftBlinkDataSet.entryCount >= MAX_ENTRIES) {
//            leftBlinkDataSet.removeFirst()
//            adjustXValues(leftBlinkDataSet)
//        }
//        if (rightBlinkDataSet.entryCount >= MAX_ENTRIES) {
//            rightBlinkDataSet.removeFirst()
//            adjustXValues(rightBlinkDataSet)
//        }
//
//        data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftBlinkScore * 10), 0)
//        data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
//        data.notifyDataChanged()
//        mChart!!.notifyDataSetChanged()
//        mChart!!.setVisibleXRangeMaximum(25f)
//        mChart!!.moveViewToX(data.entryCount.toFloat())
//    }
//}
//
//    private fun adjustXValues(dataSet: LineDataSet) {
//    for (i in 0 until dataSet.entryCount) {
//        dataSet.getEntryForIndex(i).x = i.toFloat()
//    }
//}
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
//
//    override fun onStart() {
//        super.onStart()
//        // Bind to the service
//        val intent = Intent(requireContext(), FaceLandmarkerService::class.java)
//        serviceConnection = object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
//                service = (binder as? FaceLandmarkerService.LocalBinder)?.getService()
//                service?.setDataUpdateListener(this@Analyticsfragment)
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//                service = null
//            }
//        }
//        requireContext().bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        // Unbind from the service
//        service?.setDataUpdateListener(null)
//        serviceConnection?.let { requireContext().unbindService(it) }
//    }
//
//    override fun onDataUpdate(leftBlinkScore: Float, rightBlinkScore: Float) {
//        addEntry(leftBlinkScore, rightBlinkScore)
////        Log.i("AnalyticsFragment", "Data plotted: Left: $leftBlinkScore, Right: $rightBlinkScore")
//        plotData = false
//    }
//}


//class Analyticsfragment : Fragment(){
//
//    private var mChart: LineChart? = null
//    private var plotData = true
////    private var serviceConnection: ServiceConnection? = null
////    private var service: FaceLandmarkerService? = null
//
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//
//    private val MAX_ENTRIES = 30 // Define the maximum number of entries to keep
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        val textColor = requireContext().theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.WHITE)
//
//        mChart = view.findViewById(R.id.realtimelineChart)
//        mChart!!.description.isEnabled = false
//        mChart!!.setTouchEnabled(false)
//        mChart!!.isDragEnabled = false
//        mChart!!.setScaleEnabled(false)
//        mChart!!.setDrawGridBackground(false)
//        mChart!!.setPinchZoom(false)
//        mChart!!.setBackgroundColor(Color.TRANSPARENT)
//
//        val data = LineData()
////        data.setValueTextColor(Color.WHITE)
//        data.setValueTextColor(textColor)
//        mChart!!.data = data
//
//        val l = mChart!!.legend
//        l.form = Legend.LegendForm.LINE
//        l.textColor = textColor
//
//        val xl = mChart!!.xAxis
//        xl.textColor = textColor
//        xl.setDrawGridLines(true)
//        xl.setAvoidFirstLastClipping(true)
//        xl.isEnabled = false
//
//        val leftAxis = mChart!!.axisLeft
//        leftAxis.textColor = textColor
//        leftAxis.setDrawGridLines(false)
//        leftAxis.axisMaximum = 10f
//        leftAxis.axisMinimum = -1f
//        leftAxis.setDrawGridLines(true)
//
//        val rightAxis = mChart!!.axisRight
//        rightAxis.isEnabled = false
//
//        mChart!!.axisLeft.setDrawGridLines(false)
//        mChart!!.xAxis.setDrawGridLines(false)
//        mChart!!.setDrawBorders(false)
//
//        // Initialize DataSets
//        leftBlinkDataSet = createSet("Left Blink Score", Color.MAGENTA)
//        rightBlinkDataSet = createSet("Right Blink Score", Color.CYAN)
//        data.addDataSet(leftBlinkDataSet)
//        data.addDataSet(rightBlinkDataSet)
//        addInitialEntry()
//
//        return view
//    }
//    private fun addInitialEntry() {
//        addEntry(0.1f, 0.1f)
//    }
//
//
//    private fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
//        val data = mChart!!.data
//        if (data != null) {
//            if (leftBlinkDataSet.entryCount >= MAX_ENTRIES) {
//                leftBlinkDataSet.removeFirst()
//                adjustXValues(leftBlinkDataSet)
//            }
//            if (rightBlinkDataSet.entryCount >= MAX_ENTRIES) {
//                rightBlinkDataSet.removeFirst()
//                adjustXValues(rightBlinkDataSet)
//            }
//
//            data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftBlinkScore * 10), 0)
//            data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
//            data.notifyDataChanged()
//            mChart!!.notifyDataSetChanged()
//            mChart!!.setVisibleXRangeMaximum(25f)
//            mChart!!.moveViewToX(data.entryCount.toFloat())
//        }
//    }
//
//    private fun adjustXValues(dataSet: LineDataSet) {
//        for (i in 0 until dataSet.entryCount) {
//            dataSet.getEntryForIndex(i).x = i.toFloat()
//        }
//    }
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
//
////    override fun onStart() {
////        super.onStart()
////        // Bind to the service
////        val intent = Intent(requireContext(), FaceLandmarkerService::class.java)
////        serviceConnection = object : ServiceConnection {
////            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
//////                service = (binder as? FaceLandmarkerService.LocalBinder)?.getService()
//////                service?.setDataUpdateListener(this@Analyticsfragment)
////            }
////
////            override fun onServiceDisconnected(name: ComponentName?) {
////                service = null
////            }
////        }
////        requireContext().bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
////    }
//
////    override fun onStop() {
////        super.onStop()
////        // Unbind from the service
//////        service?.setDataUpdateListener(null)
////        serviceConnection?.let { requireContext().unbindService(it) }
////    }
////
////    override fun onDataUpdate(leftBlinkScore: Float, rightBlinkScore: Float) {
////        addEntry(leftBlinkScore, rightBlinkScore)
//////        Log.i("AnalyticsFragment", "Data plotted: Left: $leftBlinkScore, Right: $rightBlinkScore")
////        plotData = false
////    }
//
//    fun setData(
//        leftBlinkScore: Float,
//        rightBlinkScore: Float
//    ) {
//        Log.i("AnalyticsFragment", "Data plotted: Left: $leftBlinkScore, Right: $rightBlinkScore")
//        addEntry(leftBlinkScore, rightBlinkScore)
//        plotData = false
//    }
//
//}


//class Analyticsfragment : Fragment(){
//
//    private var mChart: LineChart? = null
//    private var plotData = true
//
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//
//    private val MAX_ENTRIES = 30 // Define the maximum number of entries to keep
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        val textColor = requireContext().theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.WHITE)
//
//        mChart = view.findViewById(R.id.realtimelineChart)
//        mChart!!.description.isEnabled = false
//        mChart!!.setTouchEnabled(false)
//        mChart!!.isDragEnabled = false
//        mChart!!.setScaleEnabled(false)
//        mChart!!.setDrawGridBackground(false)
//        mChart!!.setPinchZoom(false)
//        mChart!!.setBackgroundColor(Color.TRANSPARENT)
//
//        val data = LineData()
//        data.setValueTextColor(textColor)
//        mChart!!.data = data
//
//        val l = mChart!!.legend
//        l.form = Legend.LegendForm.LINE
//        l.textColor = textColor
//
//        val xl = mChart!!.xAxis
//        xl.textColor = textColor
//        xl.setDrawGridLines(true)
//        xl.setAvoidFirstLastClipping(true)
//        xl.isEnabled = false
//
//        val leftAxis = mChart!!.axisLeft
//        leftAxis.textColor = textColor
//        leftAxis.setDrawGridLines(false)
//        leftAxis.axisMaximum = 10f
//        leftAxis.axisMinimum = -1f
//        leftAxis.setDrawGridLines(true)
//
//        val rightAxis = mChart!!.axisRight
//        rightAxis.isEnabled = false
//
//        mChart!!.axisLeft.setDrawGridLines(false)
//        mChart!!.xAxis.setDrawGridLines(false)
//        mChart!!.setDrawBorders(false)
//
//        // Initialize DataSets
//        leftBlinkDataSet = createSet("Left Blink Score", Color.MAGENTA)
//        rightBlinkDataSet = createSet("Right Blink Score", Color.CYAN)
//        data.addDataSet(leftBlinkDataSet)
//        data.addDataSet(rightBlinkDataSet)
//        addInitialEntry()
//
//        return view
//    }
//    private fun addInitialEntry() {
//        addEntry(0.1f, 0.1f)
//    }
//
//
//    private fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
//        val data = mChart!!.data
//        if (data != null) {
//            if (leftBlinkDataSet.entryCount >= MAX_ENTRIES) {
//                leftBlinkDataSet.removeFirst()
//                adjustXValues(leftBlinkDataSet)
//            }
//            if (rightBlinkDataSet.entryCount >= MAX_ENTRIES) {
//                rightBlinkDataSet.removeFirst()
//                adjustXValues(rightBlinkDataSet)
//            }
//
//            data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftBlinkScore * 10), 0)
//            data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
//            data.notifyDataChanged()
//            mChart!!.notifyDataSetChanged()
//            mChart!!.setVisibleXRangeMaximum(25f)
//            mChart!!.moveViewToX(data.entryCount.toFloat())
//        }
//    }
//
//    private fun adjustXValues(dataSet: LineDataSet) {
//        for (i in 0 until dataSet.entryCount) {
//            dataSet.getEntryForIndex(i).x = i.toFloat()
//        }
//    }
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
//
//
//    fun setData(
//        leftBlinkScore: Float,
//        rightBlinkScore: Float
//    ) {
//        Log.i("AnalyticsFragment", "Data plotted: Left: $leftBlinkScore, Right: $rightBlinkScore")
//        addEntry(leftBlinkScore, rightBlinkScore)
//        plotData = false
//    }
//
//}


//class Analyticsfragment : Fragment() {
//
//
//    private var mChart: LineChart? = null
//    private var plotData = true
//
//    private lateinit var leftBlinkDataSet: LineDataSet
//    private lateinit var rightBlinkDataSet: LineDataSet
//
//    private val MAX_ENTRIES = 30 // Define the maximum number of entries to keep
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//        val textColor = requireContext().theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.WHITE)
//
//        PlotManager.setAnalyticsFragment(this)
//
//        mChart = view.findViewById(R.id.realtimelineChart)
//        mChart!!.description.isEnabled = false
//        mChart!!.setTouchEnabled(false)
//        mChart!!.isDragEnabled = false
//        mChart!!.setScaleEnabled(false)
//        mChart!!.setDrawGridBackground(false)
//        mChart!!.setPinchZoom(false)
//        mChart!!.setBackgroundColor(Color.TRANSPARENT)
//
//        val data = LineData()
//        data.setValueTextColor(textColor)
//        mChart!!.data = data
//
//        val l = mChart!!.legend
//        l.form = Legend.LegendForm.LINE
//        l.textColor = textColor
//
//        val xl = mChart!!.xAxis
//        xl.textColor = textColor
//        xl.setDrawGridLines(true)
//        xl.setAvoidFirstLastClipping(true)
//        xl.isEnabled = false
//
//        val leftAxis = mChart!!.axisLeft
//        leftAxis.textColor = textColor
//        leftAxis.setDrawGridLines(false)
//        leftAxis.axisMaximum = 10f
//        leftAxis.axisMinimum = -1f
//        leftAxis.setDrawGridLines(true)
//
//        val rightAxis = mChart!!.axisRight
//        rightAxis.isEnabled = false
//
//        mChart!!.axisLeft.setDrawGridLines(false)
//        mChart!!.xAxis.setDrawGridLines(false)
//        mChart!!.setDrawBorders(false)
//
//        // Initialize DataSets
//        leftBlinkDataSet = createSet("Left Blink Score", Color.MAGENTA)
//        rightBlinkDataSet = createSet("Right Blink Score", Color.CYAN)
//        data.addDataSet(leftBlinkDataSet)
//        data.addDataSet(rightBlinkDataSet)
//        addInitialEntry()
//
//        return view
//    }
//
//    private fun addInitialEntry() {
//        addEntry(0.1f, 0.1f)
//    }
//
//    private fun addEntry(leftBlinkScore: Float, rightBlinkScore: Float) {
//        val data = mChart!!.data
//        if (data != null) {
//            if (leftBlinkDataSet.entryCount >= MAX_ENTRIES) {
//                leftBlinkDataSet.removeFirst()
//                adjustXValues(leftBlinkDataSet)
//            }
//            if (rightBlinkDataSet.entryCount >= MAX_ENTRIES) {
//                rightBlinkDataSet.removeFirst()
//                adjustXValues(rightBlinkDataSet)
//            }
//
//            data.addEntry(Entry(leftBlinkDataSet.entryCount.toFloat(), leftBlinkScore * 10), 0)
//            data.addEntry(Entry(rightBlinkDataSet.entryCount.toFloat(), rightBlinkScore * 10), 1)
//            data.notifyDataChanged()
//            mChart!!.notifyDataSetChanged()
//            mChart!!.setVisibleXRangeMaximum(25f)
//            mChart!!.moveViewToX(data.entryCount.toFloat())
//        }
//    }
//
//    private fun adjustXValues(dataSet: LineDataSet) {
//        for (i in 0 until dataSet.entryCount) {
//            dataSet.getEntryForIndex(i).x = i.toFloat()
//        }
//    }
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
//
//    fun setData(leftBlinkScore: Float, rightBlinkScore: Float) {
//        Log.i("AnalyticsFragment", "Data plotted: Left: $leftBlinkScore, Right: $rightBlinkScore")
//        addEntry(leftBlinkScore, rightBlinkScore)
//        plotData = false
//    }
//}



class Analyticsfragment : Fragment() {

    private lateinit var plotView: RealtimePlotView
    private val TAG = "AnalyticsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_analytics, container, false)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            plotView = view.findViewById(R.id.realtimelineChart)
        }


    private fun startPlotting() {
        PlotManager.startPlotting(plotView)
    }

    private fun stopPlotting() {
        PlotManager.stopPlotting()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.Analytics_fragment)
        }else{
            startPlotting()
        }
    }

    override fun onPause() {
        super.onPause()
        stopPlotting()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlotting()
    }

    override fun onStop() {
        super.onStop()
        stopPlotting()
    }
}