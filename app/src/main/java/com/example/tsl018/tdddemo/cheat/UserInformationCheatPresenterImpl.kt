package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInformationCheatPresenterImpl(val view: UserInformationCheatView, val networkClient: NetworkClientInterface) : UserInformationCheatPresenter {
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