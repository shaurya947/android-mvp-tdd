package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient : NetworkClientInterface {
    private val NetworkApi = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkApi::class.java)

    override fun getUser(): Single<User> =
            NetworkApi.getUser()
}