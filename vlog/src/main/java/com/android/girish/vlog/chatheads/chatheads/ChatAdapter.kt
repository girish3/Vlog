package com.android.girish.vlog.chatheads.chatheads

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.girish.vlog.R

class ChatAdapter(
    private val context: Context,
    var messages: List<String>
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_MESSAGE_HEADER
        } else {
            VIEW_TYPE_MESSAGE_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_MESSAGE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_header, parent, false)
            return HeaderMessageHolder(view)
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message, parent, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_HEADER -> (holder as HeaderMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_TEXT -> (holder as MessageHolder).bind(message)
        }
    }

    private inner class MessageHolder internal constructor(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val messageTextView: TextView = view.findViewById(R.id.message_body)

        internal fun bind(message: String) {
            messageTextView.text = message
        }
    }

    private inner class HeaderMessageHolder internal constructor(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val messageTextView: TextView = view.findViewById(R.id.message_header_body)
        private val authorTextView: TextView = view.findViewById(R.id.message_header_author)

        internal fun bind(message: String) {
            messageTextView.text = message

            authorTextView.text = "some author"
        }
    }

    companion object {
        private val VIEW_TYPE_MESSAGE_HEADER = 1
        private val VIEW_TYPE_MESSAGE_TEXT = 2
    }
}