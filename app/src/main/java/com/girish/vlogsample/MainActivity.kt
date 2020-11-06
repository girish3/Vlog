package com.girish.vlogsample

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.girish.vlog.Vlog
import com.android.girish.vlog.VlogModel
import com.girish.vlogsample.logger.AbstractLogger

class MainActivity : AppCompatActivity() {

    private var mVlog: Vlog = ServiceLocator.provideVlog()
    private var mLogger: AbstractLogger = ServiceLocator.provideLogger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //manageDrawOverOtherApps()
        val startButton = findViewById<Button>(R.id.start)
        val stopButton = findViewById<Button>(R.id.stop)
        val addFeed = findViewById<Button>(R.id.addFeed)
        val openLoginPage = findViewById<Button>(R.id.openLoginPage)

        startButton.setOnClickListener {
            mVlog.start(applicationContext)
        }
        stopButton.setOnClickListener { mVlog.stop() }
        addFeed.setOnClickListener {
            val vlogModels = randomLogs
            for (model in vlogModels) {
                if (mVlog.isEnabled()) {
                    mVlog.feed(model)
                }
            }
        }

        openLoginPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private val randomLogs: List<VlogModel>
        private get() = VlogDataFactory.generateLogs()

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
