package com.ikik.githubuserapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ikik.githubuserapp.R

class SplashActivity : AppCompatActivity() {
    private val second : Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_splash)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val logoImageView: ImageView = findViewById(R.id.img_logo)
        val logoAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        logoImageView.startAnimation(logoAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, second)

        val pref: SharedPreferences = getSharedPreferences("theme", Context.MODE_PRIVATE)
        val isDarkModeActive: Boolean = pref.getBoolean("is_dark_mode_active", false)
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            logoImageView.setImageResource(R.drawable.github_white)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            logoImageView.setImageResource(R.drawable.github)
        }

    }
}