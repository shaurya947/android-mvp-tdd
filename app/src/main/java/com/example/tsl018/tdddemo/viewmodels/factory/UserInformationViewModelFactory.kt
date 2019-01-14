package com.example.tsl018.tdddemo.viewmodels.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import com.example.tsl018.tdddemo.viewmodels.UserInformationViewModel
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
class UserInformationViewModelFactory(private val mainContext: CoroutineContext,
                                      private val IOContext: CoroutineContext,
                                      private val networkClient: NetworkClientInterface,
                                      private val database: DemoDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserInformationViewModel::class.java)) {
            UserInformationViewModel(mainContext, IOContext, networkClient, database) as T
        } else {
            throw IllegalArgumentException("ViewModel class not found!")
        }
    }
}