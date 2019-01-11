package com.example.tsl018.tdddemo.presenter

import com.example.tsl018.tdddemo.contract.UserInformationContract
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly
import kotlin.coroutines.CoroutineContext

class UserInformationPresenter(val view: UserInformationContract.View,
                               val mainContext: CoroutineContext,
                               val IOContext: CoroutineContext,
                               val networkClient: NetworkClientInterface,
                               val database: DemoDatabase)
    : UserInformationContract.Presenter {
    lateinit var user: User
        @TestOnly set

    override fun onIncrementButtonClicked() {
        CoroutineScope(mainContext).launch {
            val updatedUser = User(user.firstName, user.lastName, user.age.plus(1))
            withContext(IOContext) { database.userDao().updateUser(updatedUser) }
            loadUserInfo()
        }
    }

    override fun loadUserInfo() {
        CoroutineScope(mainContext).launch {
            try {
                val users = withContext(IOContext) { database.userDao().getAllUsers() }
                val user = users.firstOrNull()
                user?.let { updateUserAndShowInfo(it) } ?: run {
                    val newUser = networkClient.getUser(IOContext).await()
                    newUser?.let {
                        withContext(IOContext) { database.userDao().insert(it) }
                        updateUserAndShowInfo(it)
                    }
                }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }

    private fun updateUserAndShowInfo(userToShow: User) {
        user = userToShow
        view.showUserInfo("${user.firstName} ${user.lastName}, ${user.age}")
    }
}