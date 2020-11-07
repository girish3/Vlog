package com.girish.vlogsample.logger

import android.util.Log

class LogcatLogger : AbstractLogger() {

    override fun write(@LogPriority priority: Int, tag: String, message: String) {
        when (priority) {
            VERBOSE -> Log.v(tag, message)
            DEBUG -> Log.d(tag, message)
            INFO -> Log.i(tag, message)
            WARN -> Log.w(tag, message)
            ERROR -> Log.e(tag, message)
        }
    }
}
