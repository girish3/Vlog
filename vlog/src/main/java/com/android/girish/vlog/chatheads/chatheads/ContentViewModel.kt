package com.android.girish.vlog.chatheads.chatheads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.girish.vlog.chatheads.chatheads.filter.FilterManager
import com.android.girish.vlog.chatheads.chatheads.VLogModel.LogPriority

class ContentViewModel: ViewModel(), FilterManager.ResultListener {

    val resultObserver = MutableLiveData<List<VLogModel>>()
    val mFilterManager: FilterManager

    init {
        // TODO: use DI, isolating the dependency for now
        mFilterManager = VLog.getInstance().filterManager
        mFilterManager.setResultListener(this)
    }

    override fun onFilterResults(filterResults: List<VLogModel>) {
        resultObserver.setValue(filterResults)
    }


    /**
     * This method is called by the view when user enters filter keyword
     *
     * @param keyword
     */
    fun onKeywordEnter(keyword: String) {
        mFilterManager.setKeywordFilter(keyword)
    }


    /**
     * This method is called by the view when user sets the log priority
     *
     * @param priority
     */
    fun onPrioritySet(@LogPriority priority: Int) {
        mFilterManager.setLogPriority(priority)
    }

    fun onClearLogs() {
        mFilterManager.clearLogs()
    }

}