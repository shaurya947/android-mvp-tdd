package com.example.tsl018.tdddemo.demo

/**
 * Created by tsl018 on 2018-11-12.
 */
interface UserInformationContract {
    interface View {
        fun showUserInfo(s: String)
        fun showError()
    }

    interface Presenter {
        fun loadUserInfo()
    }
}