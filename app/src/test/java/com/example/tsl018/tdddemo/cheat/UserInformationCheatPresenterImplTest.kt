package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.BaseRxTest
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.lang.Exception

class UserInformationCheatPresenterImplTest: BaseRxTest() {
    private lateinit var presenter: UserInformationCheatPresenterImpl

    @Mock
    private lateinit var view: UserInformationCheatView

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationCheatPresenterImpl(view, networkClient)
        `when`(networkClient.getUser()).thenReturn(Single.just(User("Jamie", "Postones", 42)))
    }

    @Test
    fun invokesNetworkServiceToLoadUserInfo() {
        presenter.loadUserInfo()
        verify(networkClient).getUser()
    }

    @Test
    fun invokesViewWithUserInfoOnSuccessfulLoad() {
        presenter.loadUserInfo()
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun invokesViewWithErrorOnFailedLoad() {
        `when`(networkClient.getUser()).thenReturn(Single.error(Exception()))
        presenter.loadUserInfo()
        verify(view).showError()
    }
}