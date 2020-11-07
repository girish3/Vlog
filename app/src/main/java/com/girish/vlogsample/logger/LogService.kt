package com.girish.vlogsample.logger

import android.content.Context
import com.android.girish.vlog.Vlog

object LogService {

    private var mVlog: Vlog? = null
    private var mLogger: AbstractLogger? = null

    fun provideLogger(applicationContext: Context): AbstractLogger {
        synchronized(this) {
            if (mLogger == null) {
                mLogger = createLogger(applicationContext)
            }

            return mLogger!!
        }
    }

    fun provideVlog(applicationContext: Context): Vlog {
        synchronized(this) {
            if (mVlog == null) {
                mVlog = createVlog(applicationContext)
            }

            return mVlog!!
        }
    }

    private fun createLogger(applicationContext: Context): AbstractLogger {
        val logcatLogger = LogcatLogger()
        val vlogLogger = VlogLogger(provideVlog(applicationContext))
        logcatLogger.setNextLogger(vlogLogger)

        return logcatLogger
    }

    private fun createVlog(applicationContext: Context): Vlog {
        return Vlog.getInstance(applicationContext)
    }
}
