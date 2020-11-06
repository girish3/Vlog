package com.android.girish.vlog

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.girish.vlog.VlogAdapter.VlogViewHolder

internal class VlogAdapter : RecyclerView.Adapter<VlogViewHolder>() {
    private var mFilteredLogList: List<VlogModel>?
    private var mExpandedModel: VlogModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VlogViewHolder {
        return VlogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_log,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VlogViewHolder, position: Int) {
        val model = mFilteredLogList!![position]
        val priority = model.logPriority
        when (priority) {
            VlogModel.ERROR -> {
                holder.logTag.setTextColor(Color.RED)
                holder.logMessage.setTextColor(Color.RED)
            }
            VlogModel.WARN -> {
                holder.logTag.setTextColor(Color.parseColor("#ff9966"))
                holder.logMessage.setTextColor(Color.parseColor("#ff9966"))
            }
            else -> {
                holder.logTag.setTextColor(Color.BLACK)
                holder.logMessage.setTextColor(Color.BLACK)
            }
        }
        holder.logTag.text = getLogPriorityInitials(model.logPriority) + "/" + model.tag + ": "
        val isExpanded = model == mExpandedModel
        holder.logMessage.text = if (isExpanded) model.logMessage else if (model.logMessage.length > 30) model.logMessage.substring(0, 28) else model.logMessage
        holder.expandCollapseArrow.setImageResource(if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
        holder.expandCollapseArrow.setOnClickListener {
            mExpandedModel = if (isExpanded) null else model
            // TODO: @girish optimize if required -> update the item rather than the whole list.
            notifyDataSetChanged()
        }
    }

    private fun getLogPriorityInitials(logPriority: Int): String {
        return when (logPriority) {
            VlogModel.DEBUG -> "D"
            VlogModel.ERROR -> "E"
            VlogModel.INFO -> "I"
            VlogModel.VERBOSE -> "V"
            VlogModel.WARN -> "W"
            else -> ""
        }
    }

    override fun getItemCount(): Int {
        return if (mFilteredLogList != null) mFilteredLogList!!.size else 0
    }

    inner class VlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logTag: TextView
        var logMessage: TextView
        var expandCollapseArrow: ImageView

        init {
            logTag = itemView.findViewById(R.id.log_tag)
            logMessage = itemView.findViewById(R.id.log_message)
            expandCollapseArrow = itemView.findViewById(R.id.arrow_img)
        }
    }

    fun addLogs(logs: List<VlogModel>?) {
        mFilteredLogList = logs
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = VlogAdapter::class.java.simpleName
    }

    init {
        mFilteredLogList = ArrayList()
    }
}
