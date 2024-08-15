package com.imnexerio.eyeris.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch
import com.imnexerio.eyeris.R

class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var blinkIntervalValue: TextView
    private val blinkIntervalOptions = arrayOf("1 minutes", "2 minutes", "4 minutes", "5 minutes", "10 minutes")

    private lateinit var detectionThresholdValue: TextView
    private lateinit var trackingThresholdValue: TextView
    private lateinit var presenceThresholdValue: TextView
    private lateinit var spinnerDelegate: Spinner

    private lateinit var themeSpinner: Spinner

    private lateinit var switchBlinkReminder: MaterialSwitch
    private lateinit var switchBlinkReminderVibration: MaterialSwitch
    private lateinit var switchRunBlinkReminderScreenOff: MaterialSwitch
    private lateinit var switchTurnScreenOnNotification: MaterialSwitch
    private lateinit var switchShowOnScreenAlert: MaterialSwitch
    private lateinit var notificationSoundSpinner: Spinner


    private val MIN_CONFIDENCE = 0.2f
    private val MAX_CONFIDENCE = 0.8f
    private val STEP = 0.1f

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view = inflater.inflate(R.layout.fragment_settings, container, false)
            sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
            return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        themeSpinner = view.findViewById(R.id.theme_spinner)

        switchBlinkReminder = view.findViewById(R.id.switch_blink_reminder)
        switchBlinkReminderVibration = view.findViewById(R.id.switch_blink_reminder_vibration)
        switchRunBlinkReminderScreenOff = view.findViewById(R.id.switch_run_blink_reminder_screen_off)
        switchTurnScreenOnNotification = view.findViewById(R.id.switch_turn_screen_on_notification)
        switchShowOnScreenAlert = view.findViewById(R.id.switch_show_on_screen_alert)

        notificationSoundSpinner = view.findViewById(R.id.notification_sound_spinner)


        blinkIntervalValue = view.findViewById(R.id.blink_interval_value)
        detectionThresholdValue = view.findViewById(R.id.detection_threshold_value)
        trackingThresholdValue = view.findViewById(R.id.tracking_threshold_value)
        presenceThresholdValue = view.findViewById(R.id.presence_threshold_value)
        spinnerDelegate = view.findViewById(R.id.spinner_delegate)

        blinkIntervalValue.setOnClickListener {
            showBlinkIntervalDialog()
        }

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

        view.findViewById<Button>(R.id.about_button).setOnClickListener {
            showAboutDialog()
        }

        val currentTheme = sharedPreferences.getInt("selected_theme", 0)

        themeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position != currentTheme) {
                    sharedPreferences.edit().putInt("selected_theme", position).apply()
                    requireActivity().recreate() // Restart the activity to apply the new theme
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }


        loadSettings()
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
            putInt("spinner_delegate", spinnerDelegate.selectedItemPosition)


            putInt("selected_theme", themeSpinner.selectedItemPosition)

            putBoolean("switch_blink_reminder", switchBlinkReminder.isChecked)
            putBoolean("switch_blink_reminder_vibration", switchBlinkReminderVibration.isChecked)
            putBoolean("switch_run_blink_reminder_screen_off", switchRunBlinkReminderScreenOff.isChecked)
            putBoolean("switch_turn_screen_on_notification", switchTurnScreenOnNotification.isChecked)
            putBoolean("switch_show_on_screen_alert", switchShowOnScreenAlert.isChecked)
            putString("blink_interval", blinkIntervalValue.text.toString())


            // Save the selected notification sound
            putInt("notification_sound", notificationSoundSpinner.selectedItemPosition)


            apply()
        }
    }

    private fun loadSettings() {
        detectionThresholdValue.text = String.format("%.1f", sharedPreferences.getFloat("detection_threshold", MIN_CONFIDENCE))
        trackingThresholdValue.text = String.format("%.1f", sharedPreferences.getFloat("tracking_threshold", MIN_CONFIDENCE))
        presenceThresholdValue.text = String.format("%.1f", sharedPreferences.getFloat("presence_threshold", MIN_CONFIDENCE))
        spinnerDelegate.setSelection(sharedPreferences.getInt("spinner_delegate", 0))


        val themeSelection =resources.getStringArray(R.array.theme_options)
        val themeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, themeSelection)
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = themeAdapter
        themeSpinner.setSelection(sharedPreferences.getInt("selected_theme", 0))


        switchBlinkReminder.isChecked = sharedPreferences.getBoolean("switch_blink_reminder", true)
        switchBlinkReminderVibration.isChecked = sharedPreferences.getBoolean("switch_blink_reminder_vibration", false)
        switchRunBlinkReminderScreenOff.isChecked = sharedPreferences.getBoolean("switch_run_blink_reminder_screen_off", false)
        switchTurnScreenOnNotification.isChecked = sharedPreferences.getBoolean("switch_turn_screen_on_notification", false)
        switchShowOnScreenAlert.isChecked = sharedPreferences.getBoolean("switch_show_on_screen_alert", false)
        blinkIntervalValue.text = sharedPreferences.getString("blink_interval", "1 minutes")

        // Set up the notification sound spinner
        val notificationTones = resources.getStringArray(R.array.notification_tones)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, notificationTones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        notificationSoundSpinner.adapter = adapter
        notificationSoundSpinner.setSelection(sharedPreferences.getInt("notification_sound", 0))
    }

    private fun restoreDefaults() {
        detectionThresholdValue.text = String.format("%.1f", MIN_CONFIDENCE)
        trackingThresholdValue.text = String.format("%.1f", MIN_CONFIDENCE)
        presenceThresholdValue.text = String.format("%.1f", MIN_CONFIDENCE)
        spinnerDelegate.setSelection(0)

        themeSpinner.setSelection(0)

        switchBlinkReminder.isChecked = false
        switchBlinkReminderVibration.isChecked = false
        switchRunBlinkReminderScreenOff.isChecked = true
        switchTurnScreenOnNotification.isChecked = false
        switchShowOnScreenAlert.isChecked = false
        blinkIntervalValue.text = "1 minutes"

        // Reset the notification sound spinner
        notificationSoundSpinner.setSelection(0)

        saveSettings()
    }

    private fun showBlinkIntervalDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.blink_interval_dialog, null)
        val seekBar = dialogView.findViewById<SeekBar>(R.id.blink_interval_seekbar)
        val intervalValue = dialogView.findViewById<TextView>(R.id.blink_interval_value)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                blinkIntervalValue.text = intervalValue.text
            }
            .setNegativeButton("Cancel", null)
            .create()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                intervalValue.text = blinkIntervalOptions[progress]
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        dialog.show()
    }

    private fun showAboutDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.about_dialog, null)

        // Set up button click listeners
        dialogView.findViewById<Button>(R.id.githubButton)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/imnexerio/EyerisAndroid"))
            startActivity(intent)
        }

        dialogView.findViewById<Button>(R.id.websiteButton)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://imnexerio.github.io/eyerisweb/home.html"))
            startActivity(intent)
        }


        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .setPositiveButton("OK") { _, _ ->

            }
            .create()


        dialog.show()
    }

}
