package com.example.tsl018.tdddemo.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.network.NetworkClient

class UserInformationActivity : AppCompatActivity(), UserInformationContract.View {
    private lateinit var presenter: UserInformationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information_cheat)
        presenter = UserInformationPresenter(this, NetworkClient)
    }

    override fun onStart() {
        super.onStart()
        presenter.loadUserInfo()
    }

    override fun showUserInfo(s: String) {
    }

    override fun showError() {
    }

    fun setTestPresenter(testPresenter: UserInformationContract.Presenter) {
        presenter = testPresenter
    }
}