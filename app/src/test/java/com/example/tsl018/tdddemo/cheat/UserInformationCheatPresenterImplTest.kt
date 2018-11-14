package com.example.tsl018.tdddemo.cheat

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

class UserInformationCheatPresenterImplTest {
    private lateinit var presenter: UserInformationCheatPresenterImpl

    @Mock
    private lateinit var view: UserInformationCheatView

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var networkCall: Call<User>

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<User>>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationCheatPresenterImpl(view, networkClient)
        `when`(networkClient.getUser()).thenReturn(networkCall)
    }

    @Test
    fun invokesViewWithUserInfoOnSuccessfulLoad() {
        presenter.loadUserInfo()
        verify(networkClient).getUser()
        verify(networkCall).enqueue(callbackCaptor.capture())
        callbackCaptor.value.onResponse(networkCall,
                Response.success(User("Jamie", "Postones", 42)))
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun invokesViewWithErrorOnFailedLoad() {
        presenter.loadUserInfo()
        verify(networkClient).getUser()
        verify(networkCall).enqueue(callbackCaptor.capture())
        callbackCaptor.value.onFailure(networkCall, Exception())
        verify(view).showError()
    }
}