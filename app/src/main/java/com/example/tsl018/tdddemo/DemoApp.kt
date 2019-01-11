package com.example.tsl018.tdddemo

import android.app.Application
import android.arch.persistence.room.Room
import com.example.tsl018.tdddemo.database.DemoDatabase

class DemoApp : Application() {
    companion object {
        lateinit var DB: DemoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        DB = Room.databaseBuilder(this, DemoDatabase::class.java, "demo_database").build()
    }
}