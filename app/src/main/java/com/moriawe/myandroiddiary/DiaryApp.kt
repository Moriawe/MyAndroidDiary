package com.moriawe.myandroiddiary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}