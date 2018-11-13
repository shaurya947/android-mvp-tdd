package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.database.UserDao
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenter(val view: UserInformationContract.View,
                               val networkClient: NetworkClientInterface, val userDao: UserDao)
    : UserInformationContract.Presenter {
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
                    view.showUserInfo("${it[0].firstName} ${it[0].lastName}, ${it[0].age}")
                }, {
                    view.showError()
                })
    }
}