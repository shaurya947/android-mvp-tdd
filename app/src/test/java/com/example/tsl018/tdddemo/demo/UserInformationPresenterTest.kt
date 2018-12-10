package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Call
import retrofit2.Response

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenterTest {
    private lateinit var presenter: UserInformationPresenter

    @Mock
    private lateinit var view: UserInformationContract.View

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var userDeferred: Deferred<User?>

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationPresenter(view, networkClient, Dispatchers.Unconfined, Dispatchers.Unconfined)
        `when`(networkClient.getUser(Dispatchers.Unconfined)).thenReturn(userDeferred)
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