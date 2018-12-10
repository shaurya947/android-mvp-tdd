package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient : NetworkClientInterface {
    private val NetworkApi = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)

    override fun getUser(): Call<User> =
            NetworkApi.getUser()
}