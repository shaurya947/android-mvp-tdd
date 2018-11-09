package com.example.tsl018.tdddemo.cheat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.network.NetworkClient
import kotlinx.android.synthetic.main.activity_user_information_cheat.*
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