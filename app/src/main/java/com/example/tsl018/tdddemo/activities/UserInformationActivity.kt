package com.example.tsl018.tdddemo.activities

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tsl018.tdddemo.DemoApp
import com.example.tsl018.tdddemo.R
import com.example.tsl018.tdddemo.databinding.ActivityUserInformationBinding
import com.example.tsl018.tdddemo.network.NetworkClient
import com.example.tsl018.tdddemo.viewmodels.UserInformationViewModel
import com.example.tsl018.tdddemo.viewmodels.factory.UserInformationViewModelFactory
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.TestOnly

class UserInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInformationBinding
    private lateinit var viewModel: UserInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_information)
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(this,
                UserInformationViewModelFactory(Dispatchers.Main, Dispatchers.IO, NetworkClient, DemoApp.DB))
                .get(UserInformationViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        binding.viewModel = viewModel
    }

    @TestOnly
    fun setTestViewModel(testViewModel: UserInformationViewModel) {
        viewModel = testViewModel
    }

    @TestOnly
    fun setTestLifecycleOwner(testOwner: LifecycleOwner) {
        binding.setLifecycleOwner(testOwner)
    }
}