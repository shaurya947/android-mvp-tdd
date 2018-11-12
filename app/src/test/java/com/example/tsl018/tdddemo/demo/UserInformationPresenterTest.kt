package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenterTest {
    private lateinit var presenter: UserInformationPresenter

    @Mock
    private lateinit var view: UserInformationContract.View

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter = UserInformationPresenter(view, networkClient)
    }

    @Test
    fun invokesViewWithUserInfoOnSuccess() {
        `when`(networkClient.getUser()).thenReturn(Single.just(User("Jamie", "Postones", 42)))
        presenter.loadUserInfo()
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun invokesViewWithErrorOnException() {
        `when`(networkClient.getUser()).thenReturn(Single.error(Exception()))
        presenter.loadUserInfo()
        verify(view).showError()
    }
}