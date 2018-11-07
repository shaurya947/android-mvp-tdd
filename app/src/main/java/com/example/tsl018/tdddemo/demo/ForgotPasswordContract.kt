package com.example.tsl018.tdddemo.demo

interface ForgotPasswordContract {
    interface View {
        fun enableRequestPasswordButton()
        fun disableRequestPasswordButton()
    }

    interface Presenter {
        fun onUsernameInputChanged(s: String?)
    }
}