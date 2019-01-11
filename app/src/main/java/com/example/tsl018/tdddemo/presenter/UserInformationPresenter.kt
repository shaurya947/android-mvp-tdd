package com.example.tsl018.tdddemo.presenter

import com.example.tsl018.tdddemo.contract.UserInformationContract
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserInformationPresenter(val view: UserInformationContract.View,
                               val mainContext: CoroutineContext,
                               val IOContext: CoroutineContext,
                               val networkClient: NetworkClientInterface)
    : UserInformationContract.Presenter {
    override fun loadUserInfo() {
        CoroutineScope(mainContext).launch {
            try {
                val user = networkClient.getUser(IOContext).await()
                user?.apply { view.showUserInfo("$firstName $lastName, $age") }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }
}