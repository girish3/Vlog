package com.girish.vlogsample

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.girish.vlog.chatheads.chatheads.Vlog
import com.android.girish.vlog.chatheads.chatheads.Vlog.Companion.instance
import com.android.girish.vlog.chatheads.chatheads.VlogModel

class MainActivity : AppCompatActivity() {
    private var mVlog: Vlog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manageDrawOverOtherApps()
        mVlog = instance
        //mVlog.start(getApplicationContext());
        val startButton = findViewById<Button>(R.id.start)
        val stopButton = findViewById<Button>(R.id.stop)
        val addFeed = findViewById<Button>(R.id.addFeed)
        startButton.setOnClickListener {
            mVlog!!.start(applicationContext)
            //startActivity();
        }
        stopButton.setOnClickListener { mVlog!!.stop() }
        addFeed.setOnClickListener {
            val vlogModels = randomLogs
            for (model in vlogModels) {
                if (mVlog!!.isEnabled()) {
                    mVlog!!.feed(model)
                }
            }
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