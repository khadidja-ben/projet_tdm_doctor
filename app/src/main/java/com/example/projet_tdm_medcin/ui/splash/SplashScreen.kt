package com.example.projet_tdm_medcin.ui.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_tdm_medcin.R


class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT :Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                // Start your app main activity
                val i = Intent(this@SplashScreen, OnBoardingScreens::class.java)
                startActivity(i)

                // close this activity
                finish()
            }
        }, SPLASH_TIME_OUT)
    }
}
