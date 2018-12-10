package com.example.tsl018.tdddemo.demo

import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Call
import retrofit2.Callback
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
    private lateinit var call: Call<User>

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<User>>

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationPresenter(view, networkClient)
        `when`(networkClient.getUser()).thenReturn(call)
    }

    @Test
    fun invokesViewWithUserInfoOnSuccess() {
        presenter.loadUserInfo()
        verify(call).enqueue(callbackCaptor.capture())
        callbackCaptor.value.onResponse(call, Response.success(User("Jamie", "Postones", 42)))
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun invokesViewWithErrorOnException() {
        presenter.loadUserInfo()
        verify(call).enqueue(callbackCaptor.capture())
        callbackCaptor.value.onFailure(call, Exception())
        verify(view).showError()
    }
}