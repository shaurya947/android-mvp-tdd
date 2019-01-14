package com.example.tsl018.tdddemo.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class UserInformationViewModel(mainContext: CoroutineContext,
                                    private val IOContext: CoroutineContext,
                                    private val networkClient: NetworkClientInterface,
                                    private val database: DemoDatabase) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = mainContext + job

    open val user: LiveData<User> = Transformations.map(database.userDao().getAllUsers()) { users ->
        if (users.isNullOrEmpty()) {
            loadUserInfo()
        } else {
            isLoadingLiveData.value = false
        }
        users.firstOrNull()
    }

    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    open fun isLoading(): LiveData<Boolean> {
        if (!::isLoadingLiveData.isInitialized) {
            isLoadingLiveData = MutableLiveData()
            isLoadingLiveData.value = true
        }
        return isLoadingLiveData
    }

    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    open fun isError(): LiveData<Boolean> {
        if (!::isErrorLiveData.isInitialized) {
            isErrorLiveData = MutableLiveData()
            isErrorLiveData.value = false
        }
        return isErrorLiveData
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun loadUserInfo() {
        launch {
            try {
                val newUser = networkClient.getUser(IOContext).await()
                newUser?.let {
                    withContext(IOContext) { database.userDao().insert(it) }
                }
            } catch (e: Exception) {
                isErrorLiveData.value = true
            } finally {
                isLoadingLiveData.value = false
            }
        }
    }

    open fun onIncrementAgeClicked() {
        launch {
            user.value?.let {
                val updatedUser = User(it.firstName, it.lastName, it.age.plus(1))
                withContext(IOContext) { database.userDao().updateUser(updatedUser) }
            }
        }
    }
}