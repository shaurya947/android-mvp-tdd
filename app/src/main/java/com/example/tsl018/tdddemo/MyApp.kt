package com.example.tsl018.tdddemo

import android.app.Application
import com.example.tsl018.tdddemo.modules.presenterModule
import org.koin.android.ext.android.startKoin

open class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(presenterModule))
    }
}