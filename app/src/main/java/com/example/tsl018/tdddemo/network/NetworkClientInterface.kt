package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import io.reactivex.Single

interface NetworkClientInterface {
    fun getUser() : Single<User>
}