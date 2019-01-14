package com.example.tsl018.tdddemo.activities

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.view.View
import com.example.tsl018.tdddemo.models.User
import com.example.tsl018.tdddemo.viewmodels.UserInformationViewModel
import kotlinx.android.synthetic.main.activity_user_information.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class UserInformationActivityTest {
    private lateinit var activity: UserInformationActivity

    private lateinit var activityController: ActivityController<UserInformationActivity>

    @Mock
    private lateinit var viewModel: UserInformationViewModel

    @Mock
    private lateinit var userLiveData: LiveData<User>

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var isErrorLiveData: LiveData<Boolean>

    @Captor
    private lateinit var userObserverCaptor: ArgumentCaptor<Observer<User>>

    @Captor
    private lateinit var isLoadingObserverCaptor: ArgumentCaptor<Observer<Boolean>>

    @Captor
    private lateinit var isErrorObserverCaptor: ArgumentCaptor<Observer<Boolean>>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        `when`(viewModel.user).thenReturn(userLiveData)
        `when`(viewModel.isLoading()).thenReturn(isLoadingLiveData)
        `when`(viewModel.isError()).thenReturn(isErrorLiveData)

        activityController = Robolectric.buildActivity(UserInformationActivity::class.java)
        activity = activityController.get()

        activityController.create()
        activity.setTestViewModel(viewModel)

        activityController.start()
        verify(userLiveData).observe(ArgumentMatchers.any(LifecycleOwner::class.java), userObserverCaptor.capture())
        verify(isLoadingLiveData).observe(ArgumentMatchers.any(LifecycleOwner::class.java), isLoadingObserverCaptor.capture())
        verify(isErrorLiveData).observe(ArgumentMatchers.any(LifecycleOwner::class.java), isErrorObserverCaptor.capture())
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
    fun displaysUserInfoWhenInvoked() {
        userObserverCaptor.value.onChanged(User("Jamie", "Postones", 42))
        isErrorObserverCaptor.value.onChanged(false)
        isLoadingObserverCaptor.value.onChanged(false)
        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.VISIBLE, activity.btn_increment_age.visibility)
        assertEquals("Jamie Postones, 42", activity.info_view.text)
    }

    @Test
    fun displaysErrorWhenInvoked() {
        isErrorObserverCaptor.value.onChanged(true)
        isLoadingObserverCaptor.value.onChanged(false)
        assertEquals(View.GONE, activity.loading_view.visibility)
        assertEquals(View.VISIBLE, activity.info_view.visibility)
        assertEquals(View.GONE, activity.btn_increment_age.visibility)
        assertEquals("ERROR!", activity.info_view.text)
    }

    @Test
    fun `clicking increment age button notifies presenter`() {
        activity.btn_increment_age.performClick()
        verify(viewModel).onIncrementAgeClicked()
    }
}