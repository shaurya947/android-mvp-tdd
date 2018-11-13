package com.example.tsl018.tdddemo.demo

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

/**
 * Created by tsl018 on 2018-11-12.
 */
class UserInformationPresenterTest : BaseRxTest() {
    private lateinit var presenter: UserInformationPresenter

    @Mock
    private lateinit var view: UserInformationContract.View

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var userDao: UserDao

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationPresenter(view, networkClient, userDao)
    }

    @Test
    fun invokesViewWithUserInfoOnSuccess() {
        val user = User("Jamie", "Postones", 42)
        `when`(networkClient.getUser()).thenReturn(Single.just(user))
        `when`(userDao.getAllUsers()).thenReturn(Flowable.just(listOf(user)))
        presenter.loadUserInfo()
        verify(view).showUserInfo("Jamie Postones, 42")
        verify(userDao).insert(user)
        verify(userDao).getAllUsers()
    }

    @Test
    fun invokesViewWithErrorOnException() {
        `when`(networkClient.getUser()).thenReturn(Single.error(Exception()))
        `when`(userDao.getAllUsers()).thenReturn(Flowable.just(listOf()))
        presenter.loadUserInfo()
        verify(view).showError()
    }
}