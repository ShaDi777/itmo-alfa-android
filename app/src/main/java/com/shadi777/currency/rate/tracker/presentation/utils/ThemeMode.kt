package com.shadi777.currency.rate.tracker.presentation.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

enum class ThemeMode {
    DARK, LIGHT, SYSTEM;

    companion object {
        private const val SETTINGS_KEY = "SETTINGS_KEY"
        private const val THEME_MODE_KEY = "THEME_MODE_KEY"

        private fun applyTheme(mode: ThemeMode) {
            when (mode) {
                LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        fun getCurrentMode(context: Context): ThemeMode {
            val sharedPref = context.getSharedPreferences(
                SETTINGS_KEY,
                Context.MODE_PRIVATE
            )
            val defaultMode = SYSTEM
            val mode = sharedPref.getInt(
                THEME_MODE_KEY,
                defaultMode.ordinal
            )
            return entries[mode]
        }

        fun setCurrentMode(context: Context, mode: ThemeMode) {
            val sharedPref = context.getSharedPreferences(
                SETTINGS_KEY,
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putInt(THEME_MODE_KEY, mode.ordinal)
                apply()
            }
            applyTheme(mode)
        }
    }
}
