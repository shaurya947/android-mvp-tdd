package com.example.tsl018.tdddemo.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tsl018.tdddemo.DemoApp
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClient
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.coroutines.*
import org.jetbrains.annotations.TestOnly
import kotlin.coroutines.CoroutineContext

class UserInformationActivity : AppCompatActivity(), CoroutineScope {
    override lateinit var coroutineContext: CoroutineContext
        @TestOnly set

    lateinit var user: User
        @TestOnly set
    lateinit var IOContext: CoroutineContext
        @TestOnly set
    lateinit var database: DemoDatabase
        @TestOnly set
    lateinit var networkClient: NetworkClientInterface
        @TestOnly set

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        job = Job()
        coroutineContext = Dispatchers.Main +  job
        IOContext = Dispatchers.IO
        database = DemoApp.DB
        networkClient = NetworkClient

        btn_increment_age.setOnClickListener { onIncrementButtonClicked() }
    }

    override fun onStart() {
        super.onStart()
        loadUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun showUserInfo(s: String) {
        loading_view.visibility = View.GONE
        info_view.visibility = View.VISIBLE
        info_view.text = s
        btn_increment_age.visibility = View.VISIBLE
    }

    private fun showError() {
        loading_view.visibility = View.GONE
        info_view.visibility = View.VISIBLE
        info_view.text = "ERROR!"
    }

    private fun loadUserInfo() {
        launch {
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
                showError()
            }
        }
    }

    private fun updateUserAndShowInfo(userToShow: User) {
        user = userToShow
        showUserInfo("${user.firstName} ${user.lastName}, ${user.age}")
    }

    private fun onIncrementButtonClicked() {
        launch {
            val updatedUser = User(user.firstName, user.lastName, user.age.plus(1))
            withContext(IOContext) { database.userDao().updateUser(updatedUser) }
            loadUserInfo()
        }
    }
}