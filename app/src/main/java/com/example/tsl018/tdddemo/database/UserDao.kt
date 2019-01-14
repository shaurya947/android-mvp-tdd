package com.example.tsl018.tdddemo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.tsl018.tdddemo.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Update
    fun updateUser(user: User)
}