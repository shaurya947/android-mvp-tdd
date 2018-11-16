package com.example.tsl018.tdddemo.cheat

import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserInformationCheatPresenterImplTest {
    private lateinit var presenter: UserInformationCheatPresenterImpl

    @Mock
    private lateinit var view: UserInformationCheatView

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var userDeferred: Deferred<User?>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationCheatPresenterImpl(view, Unconfined, Unconfined, networkClient)
        `when`(networkClient.getUser(Unconfined)).thenReturn(userDeferred)
    }

    @Test
    fun invokesViewWithUserInfoOnSuccessfulLoad() = runBlocking {
        `when`(userDeferred.await()).thenReturn(User("Jamie", "Postones", 42))
        presenter.loadUserInfo()
        verify(networkClient).getUser(Unconfined)
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun invokesViewWithErrorOnFailedLoad() = runBlocking {
        `when`(userDeferred.await()).thenAnswer { throw Exception() }
        presenter.loadUserInfo()
        verify(networkClient).getUser(Unconfined)
        verify(view).showError()
    }
}