package com.example.tsl018.tdddemo.presenter

import com.example.tsl018.tdddemo.contract.UserInformationContract
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UserInformationPresenter(val view: UserInformationContract.View,
                               val mainContext: CoroutineContext,
                               val IOContext: CoroutineContext,
                               val networkClient: NetworkClientInterface,
                               val database: DemoDatabase)
    : UserInformationContract.Presenter {
    override fun loadUserInfo() {
        CoroutineScope(mainContext).launch {
            try {
                val users = withContext(IOContext) { database.userDao().getAllUsers() }
                val user = users.firstOrNull()
                user?.let { showUserInfo(it) } ?: run {
                    val newUser = networkClient.getUser(IOContext).await()
                    newUser?.let {
                        withContext(IOContext) { database.userDao().insert(it) }
                        showUserInfo(it)
                    }
                }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }

    private fun showUserInfo(user: User) {
        view.showUserInfo("${user.firstName} ${user.lastName}, ${user.age}")
    }
}