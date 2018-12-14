package com.example.tsl018.tdddemo

import android.app.Application
import com.example.tsl018.tdddemo.di.coroutineModule
import com.example.tsl018.tdddemo.di.networkModule
import com.example.tsl018.tdddemo.di.presenterModule
import org.koin.android.ext.android.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, presenterModule, coroutineModule))
    }
}