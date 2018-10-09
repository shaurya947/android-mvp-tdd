package com.example.tsl018.tdddemo.demo

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by tsl018 on 2018-10-09.
 */
class ForgotPasswordPresenterImplTest {
    private lateinit var presenter: ForgotPasswordPresenterImpl

    @Mock
    private lateinit var view: ForgotPasswordView

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        presenter = ForgotPasswordPresenterImpl(view)
    }

    @Test
    fun enableRequestPasswordButtonWhenNonEmptyInput() {
        presenter.onUsernameInputChanged("kjdhfskdf")
        verify(view).enableRequestPasswordButton()
    }

    @Test
    fun disablesRequestPasswordButtonWhenEmptyInput() {
        presenter.onUsernameInputChanged("")
        verify(view).disableRequestPasswordButton()
    }
}