package com.example.tsl018.tdddemo.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.network.NetworkClient
import kotlinx.android.synthetic.main.activity_user_information_cheat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class UserInformationActivity : AppCompatActivity(), UserInformationContract.View, CoroutineScope {
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var presenter: UserInformationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_user_information_cheat)
        presenter = UserInformationPresenter(this, NetworkClient, coroutineContext, Dispatchers.IO)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onStart() {
        super.onStart()
        presenter.loadUserInfo()
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

    fun setTestPresenter(testPresenter: UserInformationContract.Presenter) {
        presenter = testPresenter
    }
}