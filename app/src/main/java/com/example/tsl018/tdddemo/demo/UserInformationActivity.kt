package com.example.tsl018.tdddemo.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tsl018.tdddemo.R

class UserInformationActivity : AppCompatActivity(), UserInformationContract.View {
    private lateinit var presenter: UserInformationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information_cheat)
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