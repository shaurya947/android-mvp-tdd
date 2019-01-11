package com.example.tsl018.tdddemo.presenter

import com.example.tsl018.tdddemo.contract.UserInformationContract
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.database.UserDao
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserInformationPresenterTest {
    private lateinit var presenter: UserInformationPresenter

    private val user = User("Jamie", "Postones", 42)

    @Mock
    private lateinit var view: UserInformationContract.View

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var database: DemoDatabase

    @Mock
    private lateinit var userDeferred: Deferred<User?>

    @Mock
    private lateinit var userDao: UserDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = UserInformationPresenter(view, Unconfined, Unconfined, networkClient, database)
        `when`(database.userDao()).thenReturn(userDao)
        `when`(networkClient.getUser(Unconfined)).thenReturn(userDeferred)
    }

    @Test
    fun `displays user info from DB without network call`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf(user))
        presenter.loadUserInfo()
        verify(userDao).getAllUsers()
        verify(view).showUserInfo("Jamie Postones, 42")
        verifyZeroInteractions(networkClient)
    }

    @Test
    fun `retrieves and stores user info from network`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf())
        `when`(userDeferred.await()).thenReturn(user)
        presenter.loadUserInfo()
        verify(userDao).getAllUsers()
        verify(userDeferred).await()
        verify(userDao).insert(user)
        verify(view).showUserInfo("Jamie Postones, 42")
    }

    @Test
    fun `invokes view with error on DB exception`() = runBlocking {
        `when`(userDao.getAllUsers()).thenAnswer { throw Exception() }
        presenter.loadUserInfo()
        verify(view).showError()
        verifyZeroInteractions(networkClient)
    }

    @Test
    fun `invokes view with error on network exception`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf())
        `when`(userDeferred.await()).thenAnswer { throw Exception() }
        presenter.loadUserInfo()
        verify(userDao).getAllUsers()
        verify(view).showError()
    }
}