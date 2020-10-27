package com.android.girish.vlog.chatheads.chatheads.filter

import android.os.Handler
import android.os.Looper
import android.widget.Filter
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import com.android.girish.vlog.chatheads.chatheads.VLogModel.LogPriority
import com.android.girish.vlog.chatheads.chatheads.VLogModel

/**
 * Filter manager
 *
 * @property mDelay The amount of delay (in ms) before the filter process starts
 * @constructor Create empty Filter manager
 */
open class FilterManager(val mDelay: Long = 100): Filter() {

    val handler: Handler
    val mKeywordFilter = KeywordFilter()
    val mPriorityFilter = PriorityFilter()
    val mFilters: List<Criteria<VLogModel>>
    val mVlogs: MutableList<VLogModel>
    private var mResultListener: ResultListener? = null

    init {
        handler = Handler(Looper.getMainLooper())
        mFilters = ArrayList()
        mFilters.add(mKeywordFilter)
        mFilters.add(mPriorityFilter)
        mVlogs = mutableListOf()
    }

    /**
     * Initiate filter process
     *
     */
     fun initiateFilter() {

        // remove all callbacks and messages
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(object : Runnable {
            override fun run() {
                this@FilterManager.filter(null)
            }
        }, mDelay)
    }


    /**
     * For listening the filtered results
     *
     * @param resultListener
     */
    fun setResultListener(resultListener: ResultListener) {
        mResultListener = resultListener
        initiateFilter()
    }

    /**
     * pre-configures the keyword
     *
     * @param keyword
     */
    fun setKeywordFilter(keyword: String) {
        mKeywordFilter.setKeyword(keyword)
        initiateFilter()
    }

    /**
     * pre-configures the log priority
     *
     * @param priority
     */
    fun setLogPriority(@LogPriority priority: Int) {
        mPriorityFilter.setPriority(priority)
        initiateFilter()
    }

    /**
     * Result listener
     *
     * @constructor Create empty Result listener
     */
    interface ResultListener {
        /**
         * On filter results
         *
         * @param filterResults
         */
        fun onFilterResults(filterResults: List<VLogModel>)
    }

    @WorkerThread
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var filteredList: List<VLogModel> = mVlogs
        for (filter in mFilters) {
            filteredList = filter.meetCriteria(filteredList)
        }

        val filterResult = FilterResults()
        filterResult.values = filteredList
        filterResult.count = filterResult.count
        return filterResult
    }

    @UiThread
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        mResultListener?.onFilterResults(results?.values as List<VLogModel>)
    }


    /**
     * Add log to the existing log repository
     *
     * @param model
     */
    fun feedLog(model: VLogModel) {
        mVlogs.add(model)
        initiateFilter()
    }


    /**
     * Clear logs
     *
     */
    fun clearLogs() {
        mVlogs.clear()
        initiateFilter()
    }
}