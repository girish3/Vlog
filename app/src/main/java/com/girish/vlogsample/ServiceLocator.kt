package com.girish.vlogsample

import com.android.girish.vlog.Vlog
import com.girish.vlogsample.logger.AbstractLogger
import com.girish.vlogsample.logger.LogcatLogger
import com.girish.vlogsample.logger.VlogLogger

object ServiceLocator {

    private var mVlog: Vlog? = null
    private var mLogger: AbstractLogger? = null

    fun provideLogger(): AbstractLogger {
        synchronized (this) {
            if (mLogger == null) {
                mLogger = createLogger()
            }

            return mLogger!!
        }
    }

    fun provideVlog(): Vlog {
        synchronized (this) {
            if (mVlog == null) {
                mVlog = createVlog()
            }

            return mVlog!!
        }
    }

    private fun createLogger(): AbstractLogger {
        val logcatLogger = LogcatLogger()
        val vlogLogger = VlogLogger(provideVlog())
        logcatLogger.setNextLogger(vlogLogger)

        return logcatLogger
    }

    private fun createVlog(): Vlog {
        return Vlog.instance
    }
}