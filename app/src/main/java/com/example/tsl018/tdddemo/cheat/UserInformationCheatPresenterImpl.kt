package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.database.UserDao
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInformationCheatPresenterImpl(val view: UserInformationCheatView,
                                        val networkClient: NetworkClientInterface,
                                        val userDao: UserDao) : UserInformationCheatPresenter {
    override fun loadUserInfo() {
        networkClient.getUser()
                .flatMapCompletable {
                    Completable.fromRunnable {
                        userDao.insert(it)
                    }
                }
                .andThen(userDao.getAllUsers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showUserInfo("${it.firstName} ${it.lastName}, ${it.age}")
                }, {
                    view.showError()
                })
    }
}