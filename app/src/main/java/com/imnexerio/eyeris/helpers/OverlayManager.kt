package com.imnexerio.eyeris.helpers

import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import com.imnexerio.eyeris.views.OverlayView

object OverlayManager {
    private var overlayView: OverlayView? = null

    fun setOverlayView(view: OverlayView) {
        overlayView = view
    }

    fun clearOverlayView() {
        overlayView = null
    }

    fun updateOverlay(
        results: FaceLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        liveStream: RunningMode
    ) {
//        Log.i("OverlayManager", "Updating Overlay")
//        Log.i("OverlayManager", "Results: $results")
        overlayView?.setResults(results, imageHeight, imageWidth, liveStream)
    }
}
