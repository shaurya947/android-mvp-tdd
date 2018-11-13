package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInformationCheatPresenterImpl(val view: UserInformationCheatView, val networkClient: NetworkClientInterface) : UserInformationCheatPresenter {
    override fun loadUserInfo() {
        networkClient.getUser().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                view.showUserInfo("${response?.body()?.firstName} ${response?.body()?.lastName}, ${response?.body()?.age}")
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                view.showError()
            }
        })
    }
}