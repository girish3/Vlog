package com.android.girish.vlog.chatheads.chatheads;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.girish.vlog.R;

import java.util.ArrayList;
import java.util.List;

public class VLogAdapter extends RecyclerView.Adapter<VLogAdapter.VLogViewHolder> {

    private static final String TAG = VLogAdapter.class.getSimpleName();
    private List<VLogModel> mFilteredLogList;
    private VLogModel mExpandedModel = null;

    public VLogAdapter() {
        mFilteredLogList = new ArrayList<>();
    }

    @Override
    public VLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_log, parent, false));
    }

    @Override
    public void onBindViewHolder(final VLogViewHolder holder, final int position) {
        final VLogModel model = mFilteredLogList.get(position);

        int priority = model.getLogPriority();
        switch (priority) {
            case VLogModel.ERROR:
                holder.logTag.setTextColor(Color.RED);
                holder.logMessage.setTextColor(Color.RED);
                break;
            case VLogModel.WARN:
                holder.logTag.setTextColor(Color.parseColor("#ff9966"));
                holder.logMessage.setTextColor(Color.parseColor("#ff9966"));
                break;
            default:
                holder.logTag.setTextColor(Color.BLACK);
                holder.logMessage.setTextColor(Color.BLACK);
                break;
        }
        holder.logTag.setText(getLogPriorityInitials(model.getLogPriority()) + "/" + model.getTag() + ": ");
        final boolean isExpanded = model == mExpandedModel;
        holder.logMessage.setText(isExpanded ? model.getLogMessage() : model.getLogMessage().length() > 30
                ? model.getLogMessage().substring(0, 28) : model.getLogMessage());
        holder.expandCollapseArrow.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
        holder.expandCollapseArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedModel = isExpanded ? null : model;
                // TODO: @girish optimize if required -> update the item rather than the whole list.
                notifyDataSetChanged();
            }
        });
    }

    private String getLogPriorityInitials(int logPriority) {
        switch (logPriority) {
            case VLogModel.ASSERT:
                return "A";
            case VLogModel.DEBUG:
                return "D";
            case VLogModel.ERROR:
                return "E";
            case VLogModel.INFO:
                return "I";
            case VLogModel.VERBOSE:
                return "V";
            case VLogModel.WARN:
                return "W";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredLogList != null ? mFilteredLogList.size() : 0;
    }

    public class VLogViewHolder extends RecyclerView.ViewHolder {


        TextView logTag;
        TextView logMessage;
        ImageView expandCollapseArrow;

        public VLogViewHolder(View itemView) {
            super(itemView);
            logTag = itemView.findViewById(R.id.log_tag);
            logMessage = itemView.findViewById(R.id.log_message);
            expandCollapseArrow = itemView.findViewById(R.id.arrow_img);
        }
    }

    public void addLogs(List<VLogModel> logs) {
        mFilteredLogList = logs;
        notifyDataSetChanged();
    }
}