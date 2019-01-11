package com.example.tsl018.tdddemo.models

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users", primaryKeys = ["firstName", "lastName"])
data class User(@SerializedName("first_name") val firstName: String,
                @SerializedName("last_name") val lastName: String,
                val age: Int)