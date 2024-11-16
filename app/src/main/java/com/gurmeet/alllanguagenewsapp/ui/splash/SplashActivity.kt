package com.gurmeet.alllanguagenewsapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gurmeet.alllanguagenewsapp.R
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.MainActivityNewsApp
import com.gurmeet.alllanguagenewsapp.ui.mainactivity.topheadlines.TopHeadLineActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity()


    }
    private fun startActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity
            val mainIntent = Intent(this, MainActivityNewsApp::class.java)
            startActivity(mainIntent)
            // Close the splash activity so it doesn't appear on back press
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
    }

