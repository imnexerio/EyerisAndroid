package com.imnexerio.eyeris.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.imnexerio.eyeris.R

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var detectionThresholdValue: TextView
    private lateinit var trackingThresholdValue: TextView
    private lateinit var presenceThresholdValue: TextView
    private lateinit var spinnerDelegate: Spinner

    private val MIN_CONFIDENCE = 0.2f
    private val MAX_CONFIDENCE = 0.8f
    private val STEP = 0.1f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        detectionThresholdValue = view.findViewById(R.id.detection_threshold_value)
        trackingThresholdValue = view.findViewById(R.id.tracking_threshold_value)
        presenceThresholdValue = view.findViewById(R.id.presence_threshold_value)
        spinnerDelegate = view.findViewById(R.id.spinner_delegate)

        loadSettings()

        view.findViewById<AppCompatImageButton>(R.id.detection_threshold_minus).setOnClickListener {
            updateValue(detectionThresholdValue, -STEP)
        }
        view.findViewById<AppCompatImageButton>(R.id.detection_threshold_plus).setOnClickListener {
            updateValue(detectionThresholdValue, STEP)
        }
        view.findViewById<AppCompatImageButton>(R.id.tracking_threshold_minus).setOnClickListener {
            updateValue(trackingThresholdValue, -STEP)
        }
        view.findViewById<AppCompatImageButton>(R.id.tracking_threshold_plus).setOnClickListener {
            updateValue(trackingThresholdValue, STEP)
        }
        view.findViewById<AppCompatImageButton>(R.id.presence_threshold_minus).setOnClickListener {
            updateValue(presenceThresholdValue, -STEP)
        }
        view.findViewById<AppCompatImageButton>(R.id.presence_threshold_plus).setOnClickListener {
            updateValue(presenceThresholdValue, STEP)
        }

        view.findViewById<Button>(R.id.restore_defaults_button).setOnClickListener {
            restoreDefaults()
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        saveSettings()
    }

    private fun updateValue(textView: TextView, delta: Float) {
        val currentValue = textView.text.toString().toFloat()
        val newValue = (currentValue + delta).coerceIn(MIN_CONFIDENCE, MAX_CONFIDENCE)
        textView.text = String.format("%.1f", newValue)
    }

    private fun saveSettings() {
        with(sharedPreferences.edit()) {
            putFloat("detection_threshold", detectionThresholdValue.text.toString().toFloat())
            putFloat("tracking_threshold", trackingThresholdValue.text.toString().toFloat())
            putFloat("presence_threshold", presenceThresholdValue.text.toString().toFloat())
            apply()
        }
        saveSpinnerValue()
    }

    private fun loadSettings() {
        detectionThresholdValue.text = String.format("%.1f", sharedPreferences.getFloat("detection_threshold", MIN_CONFIDENCE))
        trackingThresholdValue.text = String.format("%.1f", sharedPreferences.getFloat("tracking_threshold", MIN_CONFIDENCE))
        presenceThresholdValue.text = String.format("%.1f", sharedPreferences.getFloat("presence_threshold", MIN_CONFIDENCE))
        loadSpinnerValue()
    }

    private fun saveSpinnerValue() {
        val selectedPosition = spinnerDelegate.selectedItemPosition
        with(sharedPreferences.edit()) {
            putInt("spinner_delegate", selectedPosition)
            apply()
        }
    }

    private fun loadSpinnerValue() {
        val selectedPosition = sharedPreferences.getInt("spinner_delegate", 0)
        spinnerDelegate.setSelection(selectedPosition)
    }

    private fun restoreDefaults() {
        detectionThresholdValue.text = String.format("%.1f", MIN_CONFIDENCE)
        trackingThresholdValue.text = String.format("%.1f", MIN_CONFIDENCE)
        presenceThresholdValue.text = String.format("%.1f", MIN_CONFIDENCE)
        spinnerDelegate.setSelection(0)
        saveSettings()
    }
}