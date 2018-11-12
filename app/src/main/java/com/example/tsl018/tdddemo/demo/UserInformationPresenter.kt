package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.network.NetworkClientInterface

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenter(val view: UserInformationContract.View,
                               val networkClient: NetworkClientInterface)
    : UserInformationContract.Presenter {
    override fun loadUserInfo() {
        
    }
}