package com.android.girish.vlog

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import com.android.girish.vlog.VlogService.LocalBinder
import java.util.concurrent.atomic.AtomicBoolean

class Vlog private constructor() {
    private lateinit var mApplicationContext: Context
    private val isEnabled = AtomicBoolean(false)
    private var mServiceIntent: Intent? = null
    private var total = 0
    private val MAX = 1000
    private var mService: VlogService? = null
    private val mVlogRepository = ServiceLocator.provideVlogRepository()
    private val mBound = AtomicBoolean(false)
    private val mServerConn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val service = binder as LocalBinder
            mService = service.getService()
            mBound.set(true)
            Log.d(TAG, "Service connected")
            if (isEnabled.get() && mBound.get()) {
                Log.d(TAG, "Displaying Vlog Bubble")
                mService!!.addChat()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "Service disconnected")
            mService = null
            mBound.set(false)
        }
    }

    fun isEnabled(): Boolean {
        return isEnabled.get()
    }

    private fun allowLogging(): Boolean {
        return isEnabled.get() && VlogService.sInstance != null
    }

    private fun startService() {
        mServiceIntent = Intent(mApplicationContext, VlogService::class.java)
        // TODO: is there a need to pass token as an extra?
        mApplicationContext.bindService(mServiceIntent, mServerConn, Context.BIND_AUTO_CREATE)
        mApplicationContext.startService(mServiceIntent)
    }

    // TODO: pass the context once, introduce an initializer or use builder pattern.
    fun start(context: Context) {
        mApplicationContext = context
        if (!canDrawOverOtherApp()) {
            requestDrawOverPermission()
            Log.d(TAG, "Please grant Vlog permission to draw over other apps")
            return
        }

        // Ignore if already started
        if (isEnabled.getAndSet(true)) {
            Log.d(TAG, "Vlog is already started")
            return
        }
        Log.d(TAG, "Initializing Vlog")
        startService()

        // initialize other resources if any
    }

    private fun requestDrawOverPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${mApplicationContext.packageName}"))
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        mApplicationContext.startActivity(intent)
    }

    private fun canDrawOverOtherApp(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        return Settings.canDrawOverlays(mApplicationContext)
    }

    fun feed(model: VlogModel?) {
        if (!allowLogging()) return
        if (total > MAX) {
            return
        }
        total++
        mVlogRepository.feedLog(model!!)
    }

    fun showBubble() {
        isEnabled.set(true)
        if (VlogService.sInstance != null) {
            VlogService.sInstance.addChat()
        }
    }

    fun stop() {
        if (!isEnabled.get()) {
            Log.d(TAG, "Vlog is not started")
            return
        }
        Log.d(TAG, "Stopping Vlog")
        isEnabled.set(false)
        if (mServiceIntent != null) {
            mService!!.cleanUp()
            mVlogRepository.reset()
            mApplicationContext.unbindService(mServerConn)
            mApplicationContext.stopService(mServiceIntent)
            mServiceIntent = null
        }
    }

    fun v(tag: String, msg: String) {
        val model = VlogModel(VlogModel.VERBOSE, tag, msg)
        feed(model)
    }

    fun d(tag: String, msg: String) {
        val model = VlogModel(VlogModel.DEBUG, tag, msg)
        feed(model)
    }

    fun i(tag: String, msg: String) {
        val model = VlogModel(VlogModel.INFO, tag, msg)
        feed(model)
    }

    fun w(tag: String, msg: String) {
        val model = VlogModel(VlogModel.WARN, tag, msg)
        feed(model)
    }

    fun e(tag: String, msg: String) {
        val model = VlogModel(VlogModel.ERROR, tag, msg)
        feed(model)
    }

    companion object {
        private val TAG = Vlog::class.java.simpleName
        private var vlog: Vlog? = null

        // TODO: make it thread safe.
        @JvmStatic
        val instance: Vlog
            get() {
                // TODO: make it thread safe.
                if (vlog == null) {
                    vlog = Vlog()
                }
                return vlog!!
            }
    }
}
