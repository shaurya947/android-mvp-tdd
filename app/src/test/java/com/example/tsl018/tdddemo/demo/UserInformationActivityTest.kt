package com.example.tsl018.tdddemo.demo

import android.view.View
import kotlinx.android.synthetic.main.activity_user_information_cheat.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class UserInformationActivityTest {
    private lateinit var activityController: ActivityController<UserInformationActivity>
    private lateinit var activity: UserInformationActivity

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
}