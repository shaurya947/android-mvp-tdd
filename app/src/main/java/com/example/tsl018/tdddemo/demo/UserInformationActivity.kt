package com.example.tsl018.tdddemo.demo

import android.os.Bundle
import android.view.View
import com.example.tsl018.tdddemo.R
import kotlinx.android.synthetic.main.activity_user_information_cheat.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UserInformationActivity : BaseActivity(), UserInformationContract.View {
    private val presenter: UserInformationContract.Presenter by inject { parametersOf(this, coroutineContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information_cheat)
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
}