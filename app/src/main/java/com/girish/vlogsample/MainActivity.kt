package com.girish.vlogsample

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.girish.vlog.Vlog
import com.girish.vlogsample.logger.AbstractLogger
import com.girish.vlogsample.logger.AbstractLogger.Companion.DEBUG
import com.girish.vlogsample.logger.AbstractLogger.Companion.ERROR
import com.girish.vlogsample.logger.AbstractLogger.Companion.INFO
import com.girish.vlogsample.logger.AbstractLogger.Companion.VERBOSE
import com.girish.vlogsample.logger.AbstractLogger.Companion.WARN
import com.girish.vlogsample.logger.LogService

class MainActivity : AppCompatActivity() {

    private lateinit var mVlog: Vlog
    private lateinit var mLogger: AbstractLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mVlog = LogService.provideVlog(this.applicationContext)
        mLogger = LogService.provideLogger(this.applicationContext)

        // manageDrawOverOtherApps()
        val startButton = findViewById<Button>(R.id.start)
        val stopButton = findViewById<Button>(R.id.stop)
        val addFeed = findViewById<Button>(R.id.addFeed)
        val openLoginPage = findViewById<Button>(R.id.openLoginPage)

        startButton.setOnClickListener {
            mVlog.start()
        }
        stopButton.setOnClickListener { mVlog.stop() }
        addFeed.setOnClickListener {
            logRandomMessages()
        }

        openLoginPage.setOnClickListener {
            val intent = Intent(this, TestLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logRandomMessages() {
        val sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor "

        for (i in 0..29) {
            when (i % 5) {
                0 -> mLogger.log(VERBOSE, "Surface", "Test log with verbose priority for ${i}th index $sampleText")
                1 -> mLogger.log(DEBUG, "DecorView", "Test log with Debug priority for ${i}th index $sampleText")
                2 -> mLogger.log(INFO, "Surface", "Test log with Info priority for ${i}th index $sampleText")
                3 -> mLogger.log(WARN, "DecorView", "Test log with Warn priority for ${i}th index $sampleText")
                4 -> mLogger.log(ERROR, "Choreographer", "Test log with Error priority for ${i}th index $sampleText")
            }
        }
    }

    private fun manageDrawOverOtherApps() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val REQUEST_CODE = 5469
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(intent, REQUEST_CODE)
        }
    }
}
