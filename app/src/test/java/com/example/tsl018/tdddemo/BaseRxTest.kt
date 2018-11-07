package com.example.tsl018.tdddemo

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import java.util.concurrent.Callable

/**
 * Base class for tests that involve asynchronous Rx code.
 * This will ensure that subscribes/observes that happen on different schedulers
 * in non-test environment actually happen synchronously on the same thread in tests.
 */
private val scheduler = Schedulers.trampoline()
private val handler: (Scheduler) -> Scheduler = { scheduler }
private val handler2: (Callable<Scheduler>) -> Scheduler = { scheduler }

open class BaseRxTest {
    @Before
    fun setupRxThreads() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler(handler)
        RxJavaPlugins.setComputationSchedulerHandler(handler)
        RxJavaPlugins.setInitNewThreadSchedulerHandler(handler2)
        RxJavaPlugins.setSingleSchedulerHandler(handler)
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(handler2)
    }
}