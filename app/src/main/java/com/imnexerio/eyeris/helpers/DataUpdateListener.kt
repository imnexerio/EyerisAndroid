package com.imnexerio.eyeris.helpers

interface DataUpdateListener {
    fun onDataUpdate(leftBlinkScore: Float, rightBlinkScore: Float)
}
