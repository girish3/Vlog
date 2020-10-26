package com.android.girish.vlog.chatheads.chatheads.filter
import com.android.girish.vlog.chatheads.chatheads.VLogModel

class KeywordFilter: Criteria<VLogModel> {

    var mKeyword: String = ""

    fun setKeyword(keyword: String) {
        mKeyword = keyword
    }

    override fun meetCriteria(input: List<VLogModel>): List<VLogModel> {
        if (mKeyword.isEmpty()) {
            return input
        }

        val normalizedKeyword = mKeyword.toLowerCase().trim()
        val filteredLogs = ArrayList<VLogModel>()

        for (item in input) {

            val normalizedLog = item.logMessage.toLowerCase().trim()
            val normalizedTag = item.tag.toLowerCase().trim()

            if (normalizedLog.contains(normalizedKeyword) || normalizedTag.contains(normalizedKeyword)) {
                filteredLogs.add(item)
            }
        }

        return filteredLogs
    }
}