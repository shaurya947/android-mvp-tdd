package com.example.tsl018.tdddemo.activities

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.viewmodels.UserInformationViewModel
import kotlinx.android.synthetic.main.activity_user_information.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class UserInformationActivityTest {
    private lateinit var activity: UserInformationActivity

    private lateinit var activityController: ActivityController<UserInformationActivity>

    private lateinit var lifecycleRegistry: LifecycleRegistry

    @Mock
    private lateinit var viewModel: UserInformationViewModel

    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner

    @Spy
    private lateinit var userLiveData: MutableLiveData<User>

    @Spy
    private lateinit var isLoadingLiveData: MutableLiveData<Boolean>

    @Spy
    private lateinit var isErrorLiveData: MutableLiveData<Boolean>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        isLoadingLiveData.value = true
        isErrorLiveData.value = false

        `when`(viewModel.getUser()).thenReturn(userLiveData)
        `when`(viewModel.isLoading()).thenReturn(isLoadingLiveData)
        `when`(viewModel.isError()).thenReturn(isErrorLiveData)

        lifecycleRegistry = LifecycleRegistry(lifecycleOwner)
        `when`(lifecycleOwner.lifecycle).thenReturn(lifecycleRegistry)

        activityController = Robolectric.buildActivity(UserInformationActivity::class.java)
        activity = activityController.get()

        activityController.create()
        activity.setTestViewModel(viewModel)
    }

    @Test
    fun hasVisibleLoadingViewOnCreate() {
        startActivity()

        assertEquals("loading", activity.loading_view.text)
        assertEquals(View.VISIBLE, activity.loading_view.visibility)
    }

    @Test
    fun hasHiddenInfoViewAndUpdateButtonOnCreate() {
        startActivity()

        assertEquals(View.GONE, activity.info_view.visibility)
        assertEquals(View.GONE, activity.btn_increment_age.visibility)
        assertEquals("increment age", activity.btn_increment_age.text.toString().toLowerCase())
    }

    @Test
    fun displaysUserInfoWhenInvoked() {
        userLiveData.value = User("Jamie", "Postones", 42)
        isErrorLiveData.value = false
        isLoadingLiveData.value = false

        startActivity()

        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.VISIBLE, activity.btn_increment_age.visibility)
        assertEquals("Jamie Postones, 42", activity.info_view.text)
    }

    @Test
    fun displaysErrorWhenInvoked() {
        isErrorLiveData.value = true
        isLoadingLiveData.value = false

        startActivity()

        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.GONE, activity.btn_increment_age.visibility)
        assertEquals("ERROR!", activity.info_view.text)
    }

    @Test
    fun `clicking increment age button notifies presenter`() {
        startActivity()

        activity.btn_increment_age.performClick()
        verify(viewModel).onIncrementAgeClicked()
    }

    private fun startActivity() {
        activity.setTestLifecycleOwner(lifecycleOwner)
        activityController.start()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }
}