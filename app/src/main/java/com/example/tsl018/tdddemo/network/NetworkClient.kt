package com.example.tsl018.tdddemo.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

object NetworkClient : NetworkClientInterface {
    private val NetworkApi = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)

    override fun getUser(coroutineContext: CoroutineContext) =
            CoroutineScope(coroutineContext).async {
                NetworkApi.getUser().execute().body()
            }
}