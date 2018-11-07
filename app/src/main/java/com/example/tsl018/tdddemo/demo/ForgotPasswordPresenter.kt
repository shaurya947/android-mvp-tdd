package com.example.tsl018.tdddemo.demo

/**
 * Created by tsl018 on 2018-10-09.
 */
class ForgotPasswordPresenter(val view: ForgotPasswordContract.View) : ForgotPasswordContract.Presenter {
    override fun onUsernameInputChanged(s: String?) {
        if (s.isNullOrEmpty()) view.disableRequestPasswordButton() else
            view.enableRequestPasswordButton()
    }
}