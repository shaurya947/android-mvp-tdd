package com.example.tsl018.tdddemo.cheat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.network.NetworkClient
import kotlinx.android.synthetic.main.activity_user_information_cheat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.jetbrains.annotations.TestOnly
import kotlin.coroutines.CoroutineContext

class UserInformationCheatActivity : AppCompatActivity(), UserInformationCheatView, CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var presenter: UserInformationCheatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_user_information_cheat)
        presenter = UserInformationCheatPresenterImpl(this, coroutineContext, Dispatchers.IO, NetworkClient)
    }

    override fun onStart() {
        super.onStart()
        presenter.loadUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun showUserInfo(s: String) {
        loading_view.visibility = View.GONE
        info_view.visibility = View.VISIBLE
        info_view.text = s
    }

    override fun showError() {
        loading_view.visibility = View.GONE
        info_view.visibility = View.VISIBLE
        info_view.text = "ERROR!"
    }

    @TestOnly
    fun setTestPresenter(testPresenter: UserInformationCheatPresenter) {
        presenter = testPresenter
    }
}