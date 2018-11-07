package com.example.tsl018.tdddemo.cheat

import android.view.View
import kotlinx.android.synthetic.main.activity_user_information_cheat.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class UserInformationCheatActivityTest {
    private lateinit var activityController: ActivityController<UserInformationCheatActivity>
    private lateinit var activity: UserInformationCheatActivity

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(UserInformationCheatActivity::class.java)
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