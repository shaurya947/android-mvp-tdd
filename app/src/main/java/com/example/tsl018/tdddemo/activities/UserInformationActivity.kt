package com.example.tsl018.tdddemo.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.tsl018.tdddemo.DemoApp
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.network.NetworkClient
import com.example.tsl018.tdddemo.viewmodels.UserInformationViewModel
import com.example.tsl018.tdddemo.viewmodels.factory.UserInformationViewModelFactory
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.TestOnly

class UserInformationActivity : AppCompatActivity() {
    private lateinit var viewModel: UserInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        viewModel = ViewModelProviders.of(this,
                UserInformationViewModelFactory(Dispatchers.Main, Dispatchers.IO, NetworkClient, DemoApp.DB))
                .get(UserInformationViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.getUser().observe(this, Observer { user ->
            user?.let {
                info_view.visibility = View.VISIBLE
                info_view.text = "${it.firstName} ${it.lastName}, ${it.age}"
                btn_increment_age.visibility = View.VISIBLE
            }
        })

        viewModel.isLoading().observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.isError().observe(this, Observer { isError ->
            isError?.let {
                if (it) {
                    info_view.visibility = View.VISIBLE
                    info_view.text = "ERROR!"
                }
            }
        })

        btn_increment_age.setOnClickListener { viewModel.onIncrementAgeClicked() }
    }

    @TestOnly
    fun setTestViewModel(testViewModel: UserInformationViewModel) {
        viewModel = testViewModel
    }
}