package com.girish.vlogsample

import com.android.girish.vlog.VlogModel

object VlogDataFactory {
    @JvmStatic
    fun generateLogs(): List<VlogModel> {
        val sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor "
        val logDataList = ArrayList<VlogModel>()
        var priority = VlogModel.VERBOSE
        var tag = ""
        var logMessage = ""
        for (i in 0..29) {
            if (i % 7 == 0) {
                priority = VlogModel.WARN
                tag = "DecorView"
                logMessage = "Test log with warning priority for ${i}th index ${sampleText}"
            } else if (i % 5 == 0) {
                priority = VlogModel.INFO
                tag = "Surface"
                logMessage = "Test log with info priority for ${i}th index ${sampleText}"
            } else if (i % 3 == 0) {
                priority = VlogModel.ERROR
                tag = "Choreographer"
                logMessage = "Test log with error priority for ${i}th index ${sampleText}"
            } else if (i % 2 == 0) {
                priority = VlogModel.DEBUG
                tag = "DecorView"
                logMessage = "Test log with debug priority for ${i}th index ${sampleText}"
            } else {
                priority = VlogModel.VERBOSE
                tag = "Surface"
                logMessage = "Test log with verbose priority for ${i}th index ${sampleText}"
            }
            val vlog = VlogModel(priority, tag, logMessage)
            logDataList.add(vlog)
        }
        return logDataList
    }
}
