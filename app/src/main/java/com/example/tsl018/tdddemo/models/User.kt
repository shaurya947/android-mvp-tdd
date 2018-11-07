package com.example.tsl018.tdddemo.models

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("first_name") val firstName: String,
                @SerializedName("last_name") val lastName: String,
                val age: Int)