package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient : NetworkClientInterface {
    private val NetworkApi = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)

    override fun getUser(): Deferred<User?> =
            CoroutineScope(Dispatchers.Default).async {
                NetworkApi.getUser().execute().body()
            }
}