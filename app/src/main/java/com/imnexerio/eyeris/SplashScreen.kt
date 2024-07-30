package com.imnexerio.eyeris

//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//
//import androidx.appcompat.app.AppCompatActivity
//import com.airbnb.lottie.LottieAnimationView
//
//class SplashScreen : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        this.enableEdgeToEdge()
//        setContentView(R.layout.fragment_splash)
//
//        val animationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
//
//        val iHome = Intent(this@SplashScreen, MainActivity::class.java)
//
//        Handler().postDelayed({
//            startActivity(iHome)
//            finish()
//        }, 2500)
//    }
//}



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

//class SplashScreen : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.fragment_splash)
//
//        val iHome = Intent(this@SplashScreen, MainActivity::class.java)
//
//        Handler().postDelayed({
//            startActivity(iHome)
//            finish()
//        }, 2500)
//    }
//
//}


import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        val animationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        val color = ContextCompat.getColor(this, R.color.camera_black)
        val colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        animationView.addValueCallback(
            KeyPath("**"),
            LottieProperty.COLOR_FILTER,
            LottieValueCallback<ColorFilter>(colorFilter)
        )

        val iHome = Intent(this@SplashScreen, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(iHome)
            finish()
        }, 2500)
    }
}