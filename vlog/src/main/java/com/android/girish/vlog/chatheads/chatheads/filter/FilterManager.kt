package com.android.girish.vlog.chatheads.chatheads.filter

import android.os.Handler
import android.os.Looper
import android.widget.Filter
import androidx.annotation.NonNull
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import com.android.girish.vlog.chatheads.chatheads.ILogFilter
import com.android.girish.vlog.chatheads.chatheads.VLogModel.LogPriority
import com.android.girish.vlog.chatheads.chatheads.VLogModel

/**
 * Filter manager
 *
 * @property mResultListener For listening the filtered results
 * @property mDelay The amount of delay before the filter process starts
 * @constructor Create empty Filter manager
 */
class FilterManager(val mResultListener: ResultListener, val mDelay: Long = 0): Filter() {

    val handler: Handler

    init {
        handler = Handler(Looper.getMainLooper())
    }

    /**
     * Filter
     *
     */
    open fun filter() {

        // remove all callbacks and messages
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(object : Runnable {
            override fun run() {
                this@FilterManager.filter(null)
            }
        }, mDelay)
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
        TODO("Not yet implemented")
    }

    @UiThread
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        mResultListener.onFilterResults(results?.values as List<VLogModel>)
    }
}