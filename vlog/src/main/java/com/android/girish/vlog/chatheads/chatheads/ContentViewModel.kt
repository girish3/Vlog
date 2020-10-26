package com.android.girish.vlog.chatheads.chatheads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.girish.vlog.chatheads.chatheads.filter.FilterManager

class ContentViewModel: ViewModel(), FilterManager.ResultListener {

    lateinit var mFilterManager: FilterManager
    val resultObserver = MutableLiveData<List<VLogModel>>()

    init {
        // TODO: use DI, isolating the dependency for now
        injectFilterManager()
    }

    private fun injectFilterManager() {
        mFilterManager = FilterManager(this)
    }

    override fun onFilterResults(filterResults: List<VLogModel>) {
        resultObserver.setValue(filterResults)
    }

}