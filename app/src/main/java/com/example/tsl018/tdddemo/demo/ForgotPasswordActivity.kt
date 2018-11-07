package com.example.tsl018.tdddemo.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.example.tsl018.tdddemo.R
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.jetbrains.annotations.TestOnly

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {
    override fun enableRequestPasswordButton() {
        btn_request_password.isEnabled = true
    }

    override fun disableRequestPasswordButton() {
        btn_request_password.isEnabled = false
    }

    private lateinit var presenter: ForgotPasswordContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        presenter = ForgotPasswordPresenter(this)
        supportActionBar?.title = "FORGOT PASSWORD"
        btn_request_password.isEnabled = false

        input_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onUsernameInputChanged(p0?.toString())
            }

        })
    }

    @TestOnly
    fun setTestPresenter(testPresenter: ForgotPasswordContract.Presenter) {
        presenter = testPresenter
    }
}