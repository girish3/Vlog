package com.girish.vlogsample.logger

import com.android.girish.vlog.Vlog

class VlogLogger(private val vlog: Vlog): AbstractLogger() {

    override fun write(@LogPriority priority: Int, tag: String, message: String) {
        if (!vlog.isEnabled()) {
            return
        }

        when (priority) {
            VERBOSE -> vlog.v(tag, message)
            DEBUG -> vlog.d(tag, message)
            INFO -> vlog.i(tag, message)
            WARN -> vlog.w(tag, message)
            ERROR -> vlog.e(tag, message)
        }
    }
}