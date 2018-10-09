package com.example.tsl018.tdddemo.cheat

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ForgotPasswordCheatPresenterImplTest {
    private lateinit var presenter: ForgotPasswordCheatPresenter

    @Mock
    private lateinit var view: ForgotPasswordCheatView

    @Rule
    @JvmField
    val rule : MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = ForgotPasswordCheatPresenterImpl(view)
    }

    @Test
    fun enablesRequestPasswordButtonWhenUsernameNonEmpty() {
        presenter.onUsernameInputChanged("akjhdas")
        verify(view).enableRequestPasswordButton()
    }

    @Test
    fun disablesRequestPasswordButtonWhenUsernameEmpty() {
        presenter.onUsernameInputChanged("")
        verify(view).disableRequestPasswordButton()
    }

    @Test
    fun disablesRequestPasswordButtonWhenUsernameNull() {
        presenter.onUsernameInputChanged(null)
        verify(view).disableRequestPasswordButton()
    }
}