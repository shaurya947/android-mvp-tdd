package com.example.tsl018.tdddemo.contract

interface UserInformationContract {
    interface View {
        fun showUserInfo(s: String)
        fun showError()
    }

    interface Presenter {
        fun loadUserInfo()
    }
}