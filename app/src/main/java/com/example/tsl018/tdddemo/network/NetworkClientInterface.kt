package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import retrofit2.Call

interface NetworkClientInterface {
    fun getUser() : Call<User>
}