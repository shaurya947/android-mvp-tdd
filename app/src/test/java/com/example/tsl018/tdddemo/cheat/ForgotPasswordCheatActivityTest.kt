package com.example.tsl018.tdddemo.cheat

import android.view.View
import com.example.tsl018.tdddemo.R
import kotlinx.android.synthetic.main.activity_forgot_password_cheat.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForgotPasswordCheatActivityTest {
    private lateinit var activity: ForgotPasswordCheatActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(ForgotPasswordCheatActivity::class.java)
    }

    @Test
    fun hasCorrectToolbarContents() =
        assertEquals(activity.getString(R.string.forgot_password_activity_title),
                activity.supportActionBar?.title)

    @Test
    fun hasCorrectContentsWithVisibility() {
        assertEquals(activity.getString(R.string.forgot_password_info_text_cheat),
                activity.tv_info_cheat.text)
        assertEquals(View.VISIBLE, activity.tv_info_cheat.visibility)

        assertEquals(activity.getString(R.string.input_username_hint_cheat),
                activity.input_username_cheat.hint)
        assertEquals(View.VISIBLE, activity.input_username_cheat.visibility)

        assertEquals(activity.getString(R.string.btn_request_password_text_cheat),
                activity.btn_request_password_cheat.text)
        assertEquals(View.VISIBLE, activity.btn_request_password_cheat.visibility)
        assertFalse(activity.btn_request_password_cheat.isEnabled)
    }

    // UI Story ends, functionality scenario 1 begins
    @Mock
    private lateinit var presenter: ForgotPasswordCheatPresenter

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun invokesPresenterOnUsernameInputChange() {
        activity.setTestPresenter(presenter)
        activity.input_username_cheat.setText("hjgsfjsh")
        verify(presenter).onUsernameInputChanged("hjgsfjsh")
    }

    @Test
    fun enablesRequestPasswordButtonWhenInvoked() {
        activity.enableRequestPasswordButton()
        assertTrue(activity.btn_request_password_cheat.isEnabled)
    }

    @Test
    fun disablesRequestPasswordButtonWhenInvoked() {
        activity.btn_request_password_cheat.isEnabled = true
        activity.disableRequestPasswordButton()
        assertFalse(activity.btn_request_password_cheat.isEnabled)
    }
}