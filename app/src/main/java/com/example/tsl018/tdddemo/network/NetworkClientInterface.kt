package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import kotlinx.coroutines.Deferred

interface NetworkClientInterface {
    fun getUser() : Deferred<User?>
}