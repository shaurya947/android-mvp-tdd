package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserInformationCheatPresenterImpl(val view: UserInformationCheatView,
                                        val mainContext: CoroutineContext,
                                        val IOContext: CoroutineContext,
                                        val networkClient: NetworkClientInterface)
    : UserInformationCheatPresenter {
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