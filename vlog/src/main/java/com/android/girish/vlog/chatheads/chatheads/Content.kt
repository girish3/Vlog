package com.android.girish.vlog.chatheads.chatheads

import android.content.Context
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.*
import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringSystem
import android.content.pm.PackageManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.girish.vlog.R

class Content(context: Context): LinearLayout(context) {
    private val springSystem = SpringSystem.create()
    private val scaleSpring = springSystem.createSpring()

    var messagesView: RecyclerView
    var layoutManager = LinearLayoutManager(context)
    lateinit var messagesAdapter: ChatAdapter

    val idToChannelIdMap = mutableMapOf<Int, String>()
    val channelIdToMenuIdMap = mutableMapOf<String, Int>()

    init {
        inflate(context, R.layout.chat_head_content, this)

        val list = ArrayList<String>()
        list.add("hello")
        list.add("girish")
        var messagesAdapter = ChatAdapter(this.context, list)

        messagesView = findViewById(R.id.messages)

        layoutManager.stackFromEnd = true

        messagesView.layoutManager = layoutManager
        messagesView.adapter = messagesAdapter

        val editText: EditText = findViewById(R.id.editText)

        scaleSpring.addListener(object : SimpleSpringListener() {
            override fun onSpringUpdate(spring: Spring) {
                scaleX = spring.currentValue.toFloat()
                scaleY = spring.currentValue.toFloat()
            }
        })
        scaleSpring.springConfig = SpringConfigs.CONTENT_SCALE

        scaleSpring.currentValue = 0.0
    }

    fun setInfo(chatHead: ChatHead) {
        val list = ArrayList<String>()
        list.add("new list")
        list.add("girish")
        messagesAdapter.messages = list
        messagesAdapter.notifyDataSetChanged()
        messagesView.scrollToPosition(messagesAdapter.messages.lastIndex)
    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (ignored: PackageManager.NameNotFoundException) {
        }

        return false
    }

    private fun isAppEnabled(context: Context, packageName: String): Boolean {
        var appStatus = false
        try {
            val ai = context.packageManager.getApplicationInfo(packageName, 0)
            if (ai != null) {
                appStatus = ai.enabled
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return appStatus
    }

    fun hideContent() {
        OverlayService.instance.chatHeads.handler.removeCallbacks(
            OverlayService.instance.chatHeads.showContentRunnable)

        scaleSpring.endValue = 0.0

        val anim = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 200
        anim.repeatMode = Animation.RELATIVE_TO_SELF
        startAnimation(anim)
    }

    fun showContent() {
        scaleSpring.endValue = 1.0

        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 100
        anim.repeatMode = Animation.RELATIVE_TO_SELF
        startAnimation(anim)
    }
}