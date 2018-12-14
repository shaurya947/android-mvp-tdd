package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import kotlin.coroutines.CoroutineContext

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenter(val view: UserInformationContract.View,
                               val mainContext: CoroutineContext)
    : UserInformationContract.Presenter, KoinComponent {
    override fun loadUserInfo() {
        CoroutineScope(mainContext).launch {
            try {
                val userCall = get<NetworkClientInterface>().getUser()
                userCall.await()?.let {
                    view.showUserInfo("${it.firstName} ${it.lastName}, ${it.age}")
                }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }
}