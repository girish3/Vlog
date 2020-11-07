package com.girish.vlogsample.logger

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

abstract class AbstractLogger {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(VERBOSE, DEBUG, INFO, WARN, ERROR)
    annotation class LogPriority
    companion object {
        /**
         * Priority constants
         */
        const val VERBOSE = 1
        const val DEBUG = 2
        const val INFO = 3
        const val WARN = 4
        const val ERROR = 5
    }

    protected var _nextLogger: AbstractLogger? = null

    fun setNextLogger(logger: AbstractLogger) {
        _nextLogger = logger
    }

    fun log(@LogPriority priority: Int, tag: String, message: String) {
        write(priority, tag, message)
        _nextLogger?.log(priority, tag, message)
    }

    protected abstract fun write(@LogPriority priority: Int, tag: String, message: String)
}
