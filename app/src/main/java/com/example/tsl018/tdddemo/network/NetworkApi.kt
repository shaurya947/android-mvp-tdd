package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {
    @GET("5be5ec862f000049000fc409?mocky-delay=3000ms")
    fun getUser(): Call<User>
}