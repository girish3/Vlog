package com.android.girish.vlog.chatheads.chatheads.filter
import com.android.girish.vlog.chatheads.chatheads.VlogModel.LogPriority
import com.android.girish.vlog.chatheads.chatheads.VlogModel

/**
 * Priority filter
 *
 * @constructor Create empty Priority filter
 */
class PriorityFilter: Criteria<VlogModel> {

    @LogPriority private var mPriority: Int = VlogModel.VERBOSE

    fun setPriority(@LogPriority constraint: Int) {
        mPriority = constraint
    }

    override fun meetCriteria(input: List<VlogModel>): List<VlogModel> {
        val filteredList = ArrayList<VlogModel>()

        for (item in input) {
            if (item.logPriority >= mPriority) {
                filteredList.add(item)
            }
        }

        return filteredList
    }

    override fun reset() {
        mPriority = VlogModel.VERBOSE
    }
}