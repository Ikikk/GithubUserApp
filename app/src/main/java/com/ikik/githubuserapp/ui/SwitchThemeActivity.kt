package com.ikik.githubuserapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.ikik.githubuserapp.R

class SwitchThemeActivity : AppCompatActivity() {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_switch_theme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        switchTheme = findViewById(R.id.switch_theme)
        pref = getSharedPreferences("theme", Context.MODE_PRIVATE)

        val isDarkModeActive = pref.getBoolean("is_dark_mode_active", false)
        switchTheme.isChecked = isDarkModeActive

        switchTheme.setOnCheckedChangeListener{_, isChecked ->
            pref.edit().putBoolean("is_dark_mode_active", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}