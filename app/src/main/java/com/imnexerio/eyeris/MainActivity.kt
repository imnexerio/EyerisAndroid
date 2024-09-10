package com.imnexerio.eyeris


import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.imnexerio.eyeris.databinding.ActivityMainBinding
import com.imnexerio.eyeris.fragments.AnalyticsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePosition = sharedPreferences.getInt("selected_theme", 0)
//        Log.d("Theme", themePosition.toString())
        when (themePosition) {
                0 -> setTheme( R.style.AppTheme_OceanBreeze )
                1 -> setTheme( R.style.AppTheme_SunsetGlow )
                2 -> setTheme( R.style.AppTheme_ForestWhisper )
                3 -> setTheme( R.style.AppTheme_RoyalElegance )
                4 -> setTheme( R.style.AppTheme_UrbanNight )
                5 -> setTheme( R.style.AppTheme_BlueSky )
                6 -> setTheme( R.style.AppTheme_RedBlaze )
                7 -> setTheme( R.style.AppTheme_OrangeSunset )
                8 -> setTheme( R.style.AppTheme_GreenMeadow )
                9 -> setTheme( R.style.AppTheme_PurpleTwilight )
                10 -> setTheme( R.style.AppTheme_YellowSunshine )
                11 -> setTheme( R.style.AppTheme_PinkBlossom )
                12 -> setTheme( R.style.AppTheme_Greyscale )
                else -> setTheme( R.style.AppTheme_OceanBreeze )

            }

        super.onCreate(savedInstanceState)


        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
//        activityMainBinding.navigation.setOnItemReselectedListener {
//
//        }

//        setContentView(R.layout.activity_main)

        if (intent?.getStringExtra("navigateTo") == "AnalyticsFragment") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AnalyticsFragment())
                .commit()
        }




    }
}

