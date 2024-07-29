package com.imnexerio.eyeris.helpers

import android.util.Log
import com.imnexerio.eyeris.views.RealtimePlotView


object PlotManager {
    private var realtimePlotView: RealtimePlotView? = null


    fun startPlotting(view: RealtimePlotView) {
        realtimePlotView = view
    }

    fun stopPlotting() {
        realtimePlotView = null
    }

    fun updatePlot(leftBlinkScore: Float, rightBlinkScore: Float) {
//        Log.i("PlotManager", "Left: $leftBlinkScore, Right: $rightBlinkScore")
        realtimePlotView?.addEntry(leftBlinkScore, rightBlinkScore)
    }
}