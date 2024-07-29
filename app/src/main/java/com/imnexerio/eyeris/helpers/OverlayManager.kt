package com.imnexerio.eyeris.helpers

import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import com.imnexerio.eyeris.OverlayView

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
        overlayView?.setResults(results, imageHeight, imageWidth, liveStream)
    }
}
