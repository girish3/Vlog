package com.android.girish.vlog.chatheads.chatheads;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.girish.vlog.R;

import java.util.ArrayList;
import java.util.List;

public class VlogAdapter extends RecyclerView.Adapter<VlogAdapter.VlogViewHolder> {

    private static final String TAG = VlogAdapter.class.getSimpleName();
    private List<VlogModel> mFilteredLogList;
    private VlogModel mExpandedModel = null;

    public VlogAdapter() {
        mFilteredLogList = new ArrayList<>();
    }

    @Override
    public VlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VlogViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_log, parent, false));
    }

    @Override
    public void onBindViewHolder(final VlogViewHolder holder, final int position) {
        final VlogModel model = mFilteredLogList.get(position);

        int priority = model.getLogPriority();
        switch (priority) {
            case VlogModel.ERROR:
                holder.logTag.setTextColor(Color.RED);
                holder.logMessage.setTextColor(Color.RED);
                break;
            case VlogModel.WARN:
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
            case VlogModel.ASSERT:
                return "A";
            case VlogModel.DEBUG:
                return "D";
            case VlogModel.ERROR:
                return "E";
            case VlogModel.INFO:
                return "I";
            case VlogModel.VERBOSE:
                return "V";
            case VlogModel.WARN:
                return "W";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredLogList != null ? mFilteredLogList.size() : 0;
    }

    public class VlogViewHolder extends RecyclerView.ViewHolder {


        TextView logTag;
        TextView logMessage;
        ImageView expandCollapseArrow;

        public VlogViewHolder(View itemView) {
            super(itemView);
            logTag = itemView.findViewById(R.id.log_tag);
            logMessage = itemView.findViewById(R.id.log_message);
            expandCollapseArrow = itemView.findViewById(R.id.arrow_img);
        }
    }

    public void addLogs(List<VlogModel> logs) {
        mFilteredLogList = logs;
        notifyDataSetChanged();
    }
}