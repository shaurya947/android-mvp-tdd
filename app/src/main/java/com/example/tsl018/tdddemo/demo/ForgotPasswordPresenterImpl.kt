package com.example.tsl018.tdddemo.demo

/**
 * Created by tsl018 on 2018-10-09.
 */
class ForgotPasswordPresenterImpl(val view: ForgotPasswordView) : ForgotPasswordPresenter {
    override fun onUsernameInputChanged(s: String?) {
        if (s.isNullOrEmpty()) view.disableRequestPasswordButton() else
            view.enableRequestPasswordButton()
    }
}