package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.di.networkModule
import com.example.tsl018.tdddemo.di.testCoroutineModule
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenterTest : AutoCloseKoinTest() {
    private lateinit var presenter: UserInformationPresenter

    @Mock
    private lateinit var view: UserInformationContract.View

    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var userDeferred: Deferred<User?>

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        startKoin(listOf(networkModule, testCoroutineModule))
        declareMock<NetworkClientInterface>()
        networkClient = get()

        presenter = UserInformationPresenter(view, Dispatchers.Unconfined)
        `when`(networkClient.getUser()).thenReturn(userDeferred)
    }

    @Test
    fun invokesViewWithUserInfoOnSuccess() = runBlocking {
        `when`(userDeferred.await()).thenReturn(User("Jamie", "Postones", 42))
        presenter.loadUserInfo()
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun invokesViewWithErrorOnException() = runBlocking {
        `when`(userDeferred.await()).thenAnswer { throw Exception() }
        presenter.loadUserInfo()
        verify(view).showError()
    }
}