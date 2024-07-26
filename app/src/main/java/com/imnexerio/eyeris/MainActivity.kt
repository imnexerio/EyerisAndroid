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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePosition = sharedPreferences.getInt("selected_theme", 0)
        when (themePosition) {
                0 -> setTheme( R.style.AppTheme )
                1 -> setTheme( R.style.AppTheme_OceanBreeze )
                2 -> setTheme( R.style.AppTheme_SunsetGlow )
                3 -> setTheme( R.style.AppTheme_ForestWhisper )
                4 -> setTheme( R.style.AppTheme_UrbanNight )
                5 -> setTheme( R.style.AppTheme_RoyalElegance )
            }

        super.onCreate(savedInstanceState)


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

