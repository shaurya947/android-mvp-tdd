package com.example.tsl018.tdddemo.di

import com.example.tsl018.tdddemo.network.NetworkApi
import com.example.tsl018.tdddemo.network.NetworkClient
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<NetworkClientInterface> {
        NetworkClient(Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkApi::class.java))
    }
}