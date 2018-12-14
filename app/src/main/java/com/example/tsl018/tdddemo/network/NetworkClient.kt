package com.example.tsl018.tdddemo.network

import com.example.tsl018.tdddemo.di.IO_CONTEXT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class NetworkClient(private val networkApi: NetworkApi) : NetworkClientInterface, KoinComponent {
    override fun getUser() =
            CoroutineScope(get(IO_CONTEXT)).async {
                networkApi.getUser().execute().body()
            }
}