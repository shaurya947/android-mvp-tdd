package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.BaseRxTest
import com.example.tsl018.tdddemo.DemoApp
import com.example.tsl018.tdddemo.database.UserDao
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import io.reactivex.Flowable
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

    @Mock
    private lateinit var userDao: UserDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationCheatPresenterImpl(view, networkClient, userDao)
        val user = User("Jamie", "Postones", 42)
        `when`(networkClient.getUser()).thenReturn(Single.just(user))
        `when`(userDao.getAllUsers()).thenReturn(Flowable.just(user))
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

    @Test
    fun invokesViewWithErrorOnFailedDBLoad() {
        `when`(userDao.getAllUsers()).thenReturn(Flowable.error(Exception()))
        presenter.loadUserInfo()
        verify(view).showError()
    }
}