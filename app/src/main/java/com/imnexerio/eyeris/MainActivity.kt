package com.imnexerio.eyeris


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.imnexerio.eyeris.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var isCameraRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
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

