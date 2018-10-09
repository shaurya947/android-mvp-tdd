package com.example.tsl018.tdddemo.cheat

class ForgotPasswordCheatPresenterImpl(val view: ForgotPasswordCheatView) : ForgotPasswordCheatPresenter {
    override fun onUsernameInputChanged(s: String?) =
            if (s.isNullOrBlank()) view.disableRequestPasswordButton() else
                view.enableRequestPasswordButton()
}