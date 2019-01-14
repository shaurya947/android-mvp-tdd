package com.example.tsl018.tdddemo.viewmodels

import android.arch.lifecycle.LiveData
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.database.UserDao
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import java.lang.Exception

@RunWith(RobolectricTestRunner::class)
class UserInformationViewModelTest {
    private lateinit var viewModel: UserInformationViewModel

    private lateinit var userLiveData: LiveData<User>

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    private lateinit var isErrorLiveData: LiveData<Boolean>

    private val user = User("Jamie", "Postones", 42)

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var database: DemoDatabase

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userDeferred: Deferred<User?>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        `when`(database.userDao()).thenReturn(userDao)
        `when`(networkClient.getUser(Dispatchers.Unconfined)).thenReturn(userDeferred)

        viewModel = UserInformationViewModel(Dispatchers.Unconfined, Dispatchers.Unconfined, networkClient, database)
        isLoadingLiveData = viewModel.isLoading()
        isErrorLiveData = viewModel.isError()
    }

    @Test
    fun `initial loading value is set to true`() {
        val isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertTrue(it) }
    }

    @Test
    fun `initial error value is set to false`() {
        val isError = isErrorLiveData.value
        assertNotNull(isError)
        isError?.let { assertFalse(it) }
    }

    @Test
    fun `does network call to get user if not locally present`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf())
        `when`(userDeferred.await()).thenReturn(user)

        userLiveData = viewModel.getUser()
        verify(networkClient).getUser(Dispatchers.Unconfined)
        verify(userDao).insert(user)
        assertEquals(user, userLiveData.value)

        val isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }

        val isError = isErrorLiveData.value
        assertNotNull(isError)
        isError?.let { assertFalse(it) }
        return@runBlocking
    }

    @Test
    fun `retrieves local user without network call if present`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf(user))

        userLiveData = viewModel.getUser()
        verify(userDao).getAllUsers()
        verifyZeroInteractions(networkClient)
        assertEquals(user, userLiveData.value)

        val isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }

        val isError = isErrorLiveData.value
        assertNotNull(isError)
        isError?.let { assertFalse(it) }
        return@runBlocking
    }

    @Test
    fun `throws error if problem loading user from network`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf())
        `when`(userDeferred.await()).thenAnswer { throw Exception() }

        userLiveData = viewModel.getUser()
        verify(networkClient).getUser(Dispatchers.Unconfined)

        val isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }

        val isError = isErrorLiveData.value
        assertNotNull(isError)
        isError?.let { assertTrue(it) }
        return@runBlocking
    }

    @Test
    fun `throws error if problem loading user from database`() = runBlocking {
        `when`(userDao.getAllUsers()).thenAnswer { throw Exception() }

        userLiveData = viewModel.getUser()
        verify(userDao).getAllUsers()
        verifyZeroInteractions(networkClient)

        val isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }

        val isError = isErrorLiveData.value
        assertNotNull(isError)
        isError?.let { assertTrue(it) }
        return@runBlocking
    }

    @Test
    fun `updates user age in database when invoked`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf(user))
        userLiveData = viewModel.getUser()

        viewModel.onIncrementAgeClicked()
        verify(userDao).updateUser(User(user.firstName, user.lastName, user.age.plus(1)))
    }
}