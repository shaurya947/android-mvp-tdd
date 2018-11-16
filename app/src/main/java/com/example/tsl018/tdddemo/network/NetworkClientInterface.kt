package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.models.User
import kotlinx.coroutines.Deferred
import kotlin.coroutines.CoroutineContext

interface NetworkClientInterface {
    fun getUser(coroutineContext: CoroutineContext): Deferred<User?>
}