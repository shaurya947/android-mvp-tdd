package com.example.tsl018.tdddemo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.tsl018.tdddemo.models.User

@Database(entities = [User::class], version = 1)
abstract class DemoDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}