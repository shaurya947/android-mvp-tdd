package com.example.tsl018.tdddemo.cheat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.tsl018.tdddemo.R
import kotlinx.android.synthetic.main.activity_forgot_password_cheat.*
import org.jetbrains.annotations.TestOnly

class ForgotPasswordCheatActivity : AppCompatActivity(), ForgotPasswordCheatView {
    override fun enableRequestPasswordButton() {
        btn_request_password_cheat.isEnabled = true
    }

    override fun disableRequestPasswordButton() {
        btn_request_password_cheat.isEnabled = false
    }

    private lateinit var presenter : ForgotPasswordCheatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_cheat)
        presenter = ForgotPasswordCheatPresenterImpl(this)
        supportActionBar?.title = getString(R.string.forgot_password_activity_title)

        btn_request_password_cheat.isEnabled = false
        input_username_cheat.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                    presenter.onUsernameInputChanged(p0?.toString())
        })
    }

    @TestOnly
    fun setTestPresenter(testPresenter: ForgotPasswordCheatPresenter) {
        presenter = testPresenter
    }
}
