package com.example.tsl018.tdddemo.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module
import kotlin.coroutines.CoroutineContext

const val IO_CONTEXT = "IO"

val coroutineModule = module {
    single<CoroutineContext>(IO_CONTEXT) { Dispatchers.IO }
}