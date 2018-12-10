package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenter(val view: UserInformationContract.View,
                               val networkClient: NetworkClientInterface)
    : UserInformationContract.Presenter {
    override fun loadUserInfo() {
        networkClient.getUser().enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                view.showError()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    view.showUserInfo("${it.firstName} ${it.lastName}, ${it.age}")
                }
            }

        })
    }
}