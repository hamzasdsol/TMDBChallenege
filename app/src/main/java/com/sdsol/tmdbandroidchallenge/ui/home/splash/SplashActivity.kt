package com.sdsol.tmdbandroidchallenge.ui.home.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.sdsol.tmdbandroidchallenge.R
import com.sdsol.tmdbandroidchallenge.ui.home.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }, 2000)
    }
}