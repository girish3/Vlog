package com.android.girish.vlog.chatheads.chatheads.filter
import com.android.girish.vlog.chatheads.chatheads.VLogModel.LogPriority
import com.android.girish.vlog.chatheads.chatheads.VLogModel

/**
 * Priority filter
 *
 * @constructor Create empty Priority filter
 */
private class PriorityFilter: Criteria<VLogModel> {

    @LogPriority private var mPriority: Int = VLogModel.VERBOSE

    fun setPriority(@LogPriority constraint: Int) {
        mPriority = constraint
    }

    override fun meetCriteria(input: List<VLogModel>): List<VLogModel> {
        val filteredList = ArrayList<VLogModel>()

        for (item in input) {
            if (item.logPriority >= mPriority) {
                filteredList.add(item)
            }
        }

        return filteredList
    }
}