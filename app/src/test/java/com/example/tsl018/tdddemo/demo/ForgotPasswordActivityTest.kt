package com.example.tsl018.tdddemo.demo

import android.view.View
import android.widget.TextView
import com.example.tsl018.tdddemo.R
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password_cheat.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Created by tsl018 on 2018-10-09.
 */
@RunWith(RobolectricTestRunner::class)
class ForgotPasswordActivityTest {
    private lateinit var activity: ForgotPasswordActivity

    @Mock
    private lateinit var presenter: ForgotPasswordPresenter

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(ForgotPasswordActivity::class.java)
    }

    @Test
    fun hasRightToolbarTitle() {
        assertEquals("FORGOT PASSWORD", activity.supportActionBar?.title)
    }

    @Test
    fun hasRightContents() {
        assertEquals(activity.getString(R.string.tv_info_text), activity.tv_info.text)
        assertEquals(View.VISIBLE, activity.tv_info.visibility)

        assertEquals("Enter your username", activity.input_username.hint)
        assertEquals(View.VISIBLE, activity.input_username.visibility)

        assertEquals("Request password", activity.btn_request_password.text)
        assertEquals(View.VISIBLE, activity.btn_request_password.visibility)
        assertFalse(activity.btn_request_password.isEnabled)
    }

    @Test
    fun invokesPresenterOnTextChange() {
        activity.setTestPresenter(presenter)
        activity.input_username.setText("kjdhksdfs")
        verify(presenter).onUsernameInputChanged("kjdhksdfs")
    }

    @Test
    fun enablesRequestPasswordButtonWhenInvoked() {
        activity.enableRequestPasswordButton()

        assertTrue(activity.btn_request_password.isEnabled)
    }

    @Test
    fun disablesRequestPasswordButtonWhenInvoked() {
        activity.btn_request_password.isEnabled = true
        activity.disableRequestPasswordButton()

        assertFalse(activity.btn_request_password.isEnabled)
    }
}