package com.example.tsl018.tdddemo.activities

import android.view.View
import com.example.tsl018.tdddemo.contract.UserInformationContract
import kotlinx.android.synthetic.main.activity_user_information.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class UserInformationActivityTest {
    private lateinit var activityController: ActivityController<UserInformationActivity>
    private lateinit var activity: UserInformationActivity

    @Mock
    private lateinit var presenter: UserInformationContract.Presenter

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(UserInformationActivity::class.java)
        activity = activityController.get()

        activityController.create()
    }

    @Test
    fun hasVisibleLoadingViewOnCreate() {
        assertEquals("loading", activity.loading_view.text)
        assertEquals(View.VISIBLE, activity.loading_view.visibility)
    }

    @Test
    fun hasHiddenInfoViewOnCreate() {
        assertEquals(View.GONE, activity.info_view.visibility)
    }

    @Test
    fun invokesPresenterOnStart() {
        activity.setTestPresenter(presenter)
        activityController.start()
        verify(presenter).loadUserInfo()
    }

    @Test
    fun displaysUserInfoWhenInvoked() {
        activity.showUserInfo("Jamie Postones, 42")
        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals("Jamie Postones, 42", activity.info_view.text)
    }

    @Test
    fun displaysErrorWhenInvoked() {
        activity.showError()
        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals("ERROR!", activity.info_view.text)
    }
}