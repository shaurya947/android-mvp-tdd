package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInformationCheatPresenterImpl(val view: UserInformationCheatView, val networkClient: NetworkClientInterface) : UserInformationCheatPresenter {
    override fun loadUserInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = networkClient.getUser().await()
                user?.apply { view.showUserInfo("$firstName $lastName, $age") }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }
}