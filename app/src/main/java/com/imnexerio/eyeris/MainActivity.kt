package com.imnexerio.eyeris


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.imnexerio.eyeris.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var isCameraRunning = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(activityMainBinding.root)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePosition = sharedPreferences.getInt("selected_theme", 0)
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)
        Log.i("MainActivity", "Dark mode: $darkMode")
        Log.i("MainActivity", "Theme position: $themePosition")
        when (themePosition) {
                0 -> setTheme(if (darkMode) R.style.AppTheme_Dark else R.style.AppTheme_Light)
                1 -> setTheme(if (darkMode) R.style.AppTheme_OceanBreeze_Dark else R.style.AppTheme_OceanBreeze_Light)
                2 -> setTheme(if (darkMode) R.style.AppTheme_SunsetGlow_Dark else R.style.AppTheme_SunsetGlow_Light)
                3 -> setTheme(if (darkMode) R.style.AppTheme_ForestWhisper_Dark else R.style.AppTheme_ForestWhisper_Light)
                4 -> setTheme(if (darkMode) R.style.AppTheme_UrbanNight_Dark else R.style.AppTheme_UrbanNight_Light)
                5 -> setTheme(if (darkMode) R.style.AppTheme_RoyalElegance_Dark else R.style.AppTheme_RoyalElegance_Light)
            }

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopFaceLandmarkerService()
    }


//    private fun startFaceLandmarkerService() {
//        val serviceIntent = Intent(this, FaceLandmarkerService::class.java).apply {
//            action = "ACTION_START_CAMERA"
//        }
//        ContextCompat.startForegroundService(this, serviceIntent)
//        isCameraRunning = true
//
//    }

        private fun stopFaceLandmarkerService() {
        val serviceIntent = Intent(this, FaceLandmarkerService::class.java).apply {
            action = "ACTION_STOP_CAMERA"
        }
        ContextCompat.startForegroundService(this, serviceIntent)
        isCameraRunning = false

    }

}

