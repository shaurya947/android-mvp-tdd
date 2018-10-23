package com.example.tsl018.tdddemo.modules

import com.example.tsl018.tdddemo.demo.ForgotPasswordPresenter
import com.example.tsl018.tdddemo.demo.ForgotPasswordPresenterImpl
import com.example.tsl018.tdddemo.demo.ForgotPasswordView
import org.koin.dsl.module.module

val presenterModule = module {
    factory("a") { (view: ForgotPasswordView) -> ForgotPasswordPresenterImpl(view) as ForgotPasswordPresenter }
}