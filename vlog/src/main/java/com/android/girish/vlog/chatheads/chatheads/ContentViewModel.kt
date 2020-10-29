package com.android.girish.vlog.chatheads.chatheads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.girish.vlog.chatheads.chatheads.filter.VlogRepository
import com.android.girish.vlog.chatheads.chatheads.VLogModel.LogPriority

class ContentViewModel: ViewModel(), VlogRepository.ResultListener {

    val resultObserver = MutableLiveData<List<VLogModel>>()
    val mVlogRepository: VlogRepository

    init {
        // TODO: use DI, isolating the dependency for now
        mVlogRepository = VLog.getInstance().vlogRepository
        mVlogRepository.setResultListener(this)
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
        mVlogRepository.setKeywordFilter(keyword)
    }


    /**
     * This method is called by the view when user sets the log priority
     *
     * @param priority
     */
    fun onPrioritySet(@LogPriority priority: Int) {
        mVlogRepository.setLogPriority(priority)
    }

    fun onClearLogs() {
        mVlogRepository.clearLogs()
    }

}