package com.example.tsl018.tdddemo.activities

import android.view.View
import com.example.tsl018.tdddemo.database.DemoDatabase
import com.example.tsl018.tdddemo.database.UserDao
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.network.NetworkClientInterface
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class UserInformationActivityTest {
    private lateinit var activityController: ActivityController<UserInformationActivity>
    private lateinit var activity: UserInformationActivity

    private val user = User("Jamie", "Postones", 42)


    @Mock
    private lateinit var database: DemoDatabase

    @Mock
    private lateinit var networkClient: NetworkClientInterface

    @Mock
    private lateinit var userDeferred: Deferred<User?>

    @Mock
    private lateinit var userDao: UserDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        `when`(database.userDao()).thenReturn(userDao)
        `when`(networkClient.getUser(Dispatchers.Unconfined)).thenReturn(userDeferred)

        activityController = Robolectric.buildActivity(UserInformationActivity::class.java)
        activity = activityController.get()

        activityController.create()
        activity.coroutineContext = Dispatchers.Unconfined
        activity.IOContext = Dispatchers.Unconfined
        activity.database = database
        activity.networkClient = networkClient
    }

    @Test
    fun hasVisibleLoadingViewOnCreate() {
        assertEquals("loading", activity.loading_view.text)
        assertEquals(View.VISIBLE, activity.loading_view.visibility)
    }

    @Test
    fun hasHiddenInfoViewAndUpdateButtonOnCreate() {
        assertEquals(View.GONE, activity.info_view.visibility)
        assertEquals(View.GONE, activity.btn_increment_age.visibility)
        assertEquals("increment age", activity.btn_increment_age.text.toString().toLowerCase())
    }

    @Test
    fun `displays user information from network when not available locally`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf())
        `when`(userDeferred.await()).thenReturn(user)
        activityController.start()
        verify(userDao).getAllUsers()
        verify(userDeferred).await()
        verify(userDao).insert(user)

        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.VISIBLE, activity.btn_increment_age.visibility)
        assertEquals("Jamie Postones, 42", activity.info_view.text)
    }

    @Test
    fun `displays user information from DB if available`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf(user))
        activityController.start()
        verify(userDao).getAllUsers()
        verifyZeroInteractions(networkClient)

        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.VISIBLE, activity.btn_increment_age.visibility)
        assertEquals("Jamie Postones, 42", activity.info_view.text)
    }

    @Test
    fun `displays error in case of DB exception`() = runBlocking {
        `when`(userDao.getAllUsers()).thenAnswer { throw Exception() }
        activityController.start()
        verifyZeroInteractions(networkClient)

        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.GONE, activity.btn_increment_age.visibility)
        assertEquals("ERROR!", activity.info_view.text)
    }

    @Test
    fun `displays error in case of network exception`() = runBlocking {
        `when`(userDao.getAllUsers()).thenReturn(listOf())
        `when`(userDeferred.await()).thenAnswer { throw Exception() }
        activityController.start()
        verify(userDao).getAllUsers()

        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.GONE, activity.btn_increment_age.visibility)
        assertEquals("ERROR!", activity.info_view.text)
    }

    @Test
    fun `clicking increment age button updates user`() {
        `when`(userDao.getAllUsers()).thenReturn(listOf(user))
        activityController.start()

        val updatedUser = User(user.firstName, user.lastName, user.age.plus(1))
        `when`(userDao.getAllUsers()).thenReturn(listOf(updatedUser))
        activity.btn_increment_age.performClick()

        verify(userDao).updateUser(updatedUser)
        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.VISIBLE, activity.btn_increment_age.visibility)
        assertEquals("Jamie Postones, 43", activity.info_view.text)
    }
}