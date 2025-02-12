package com.shadi777.currency.rate.tracker

import android.app.Application
import com.shadi777.currency.rate.tracker.presentation.utils.ThemeMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeMode.setCurrentMode(this, ThemeMode.getCurrentMode(context = this))
    }
}
