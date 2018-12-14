package com.example.tsl018.tdddemo.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module
import kotlin.coroutines.CoroutineContext

val testCoroutineModule = module {
    single(IO_CONTEXT) { Dispatchers.Unconfined as CoroutineContext }
}