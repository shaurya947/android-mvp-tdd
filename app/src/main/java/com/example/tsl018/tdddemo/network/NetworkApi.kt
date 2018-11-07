package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkApi {
    @GET("http://www.mocky.io/v2/5be3140e2f00004300ca2251")
    fun getUser(): Single<User>
}