package com.example.tsl018.tdddemo.di

import com.example.tsl018.tdddemo.demo.UserInformationContract
import com.example.tsl018.tdddemo.demo.UserInformationPresenter
import org.koin.dsl.module.module
import kotlin.coroutines.CoroutineContext

val presenterModule = module {
    factory<UserInformationContract.Presenter> { (view: UserInformationContract.View, mainContext: CoroutineContext) -> UserInformationPresenter(view, mainContext) }
}