package com.girish.vlogsample

import android.content.Context
import com.android.girish.vlog.Vlog
import com.girish.vlogsample.logger.AbstractLogger
import com.girish.vlogsample.logger.LogcatLogger
import com.girish.vlogsample.logger.VlogLogger

object ServiceLocator {

    private var mVlog: Vlog? = null
    private var mLogger: AbstractLogger? = null

    fun provideLogger(context: Context): AbstractLogger {
        synchronized(this) {
            if (mLogger == null) {
                mLogger = createLogger(context)
            }

            return mLogger!!
        }
    }

    fun provideVlog(context: Context): Vlog {
        synchronized(this) {
            if (mVlog == null) {
                mVlog = createVlog(context)
            }

            return mVlog!!
        }
    }

    private fun createLogger(context: Context): AbstractLogger {
        val logcatLogger = LogcatLogger()
        val vlogLogger = VlogLogger(provideVlog(context))
        logcatLogger.setNextLogger(vlogLogger)

        return logcatLogger
    }

    private fun createVlog(context: Context): Vlog {
        return Vlog.getInstance(context)
    }
}
