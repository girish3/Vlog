package com.girish.vlogsample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.girish.vlogsample.logger.AbstractLogger
import com.girish.vlogsample.logger.AbstractLogger.Companion.DEBUG
import com.girish.vlogsample.logger.AbstractLogger.Companion.ERROR
import com.girish.vlogsample.logger.AbstractLogger.Companion.VERBOSE
import com.girish.vlogsample.logger.LogService
import com.google.android.material.snackbar.Snackbar

class TestLoginActivity : AppCompatActivity() {

    private lateinit var mLogger: AbstractLogger
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLogger = LogService.provideLogger(this.applicationContext)

        val userIdField = findViewById<View>(R.id.userId)
        val pwdField = findViewById<View>(R.id.passwordField)
        val loginButton = findViewById<View>(R.id.loginButton)

        userIdField.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                mLogger.log(VERBOSE, TAG, "User Id field is in focused state")
            }
        }

        pwdField.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                mLogger.log(VERBOSE, TAG, "Password field is in focused state")
            }
        }

        userIdField.setOnClickListener {
            mLogger.log(DEBUG, TAG, "User Id field was clicked")
        }

        pwdField.setOnClickListener {
            mLogger.log(DEBUG, TAG, "Password field was clicked")
        }

        loginButton.setOnClickListener {
            val isSuccessful = initiateLogin()
            if (!isSuccessful) {
                Snackbar.make(loginButton, "Something went wrong. Please try again", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun initiateLogin(): Boolean {
        // simulating error
        mLogger.log(ERROR, TAG, "Auth Server responded with 500 status code: Internal error")
        return false
    }
}
