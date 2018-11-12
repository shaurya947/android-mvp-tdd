package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenter(val view: UserInformationContract.View,
                               val networkClient: NetworkClientInterface)
    : UserInformationContract.Presenter {
    override fun loadUserInfo() {
        networkClient.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showUserInfo("${it.firstName} ${it.lastName}, ${it.age}")
                }, {
                    view.showError()
                })
    }
}