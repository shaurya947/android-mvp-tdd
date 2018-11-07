package com.example.tsl018.tdddemo.cheat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.network.NetworkClient
import org.jetbrains.annotations.TestOnly

class UserInformationCheatActivity : AppCompatActivity(), UserInformationCheatView {
    private lateinit var presenter: UserInformationCheatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information_cheat)
        presenter = UserInformationCheatPresenterImpl(this, NetworkClient)
    }

    override fun onStart() {
        super.onStart()
        presenter.loadUserInfo()
    }

    override fun showUserInfo(s: String) {
    }

    override fun showError() {
    }

    @TestOnly
    fun setTestPresenter(testPresenter: UserInformationCheatPresenter) {
        presenter = testPresenter
    }
}