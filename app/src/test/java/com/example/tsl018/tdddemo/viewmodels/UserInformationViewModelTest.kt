package com.example.tsl018.tdddemo.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
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
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserInformationViewModelTest {
    private lateinit var viewModel: UserInformationViewModel

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

    @Spy
    private val userListLiveData: MutableLiveData<List<User>> = MutableLiveData()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        `when`(database.userDao()).thenReturn(userDao)
        `when`(userDao.getAllUsers()).thenReturn(userListLiveData)
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
        `when`(userDeferred.await()).thenReturn(user)
        userListLiveData.value = listOf()

        viewModel.user.observeForever { }
        verify(networkClient).getUser(Dispatchers.Unconfined)
        verify(userDao).insert(user)

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
        userListLiveData.value = listOf(user)

        viewModel.user.observeForever { }
        verify(userDao).getAllUsers()
        verifyZeroInteractions(networkClient)

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
        userListLiveData.value = listOf()
        `when`(userDeferred.await()).thenAnswer { throw Exception() }

        viewModel.user.observeForever { }
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
    fun `throws error if problem inserting user into database`() = runBlocking {
        userListLiveData.value = listOf()
        `when`(userDeferred.await()).thenReturn(user)
        `when`(userDao.insert(user)).thenAnswer { throw Exception() }

        viewModel.user.observeForever { }
        verify(userDao).getAllUsers()
        verify(networkClient).getUser(Dispatchers.Unconfined)
        verify(userDao).insert(user)

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
        userListLiveData.value = listOf(user)
        viewModel.user.observeForever { }

        viewModel.onIncrementAgeClicked()
        verify(userDao).updateUser(User(user.firstName, user.lastName, user.age.plus(1)))
    }
}