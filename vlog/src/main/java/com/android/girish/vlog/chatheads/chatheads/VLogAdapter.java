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

public class VLogAdapter extends RecyclerView.Adapter<VLogAdapter.VLogViewHolder> implements Filterable {

    private static final String TAG = VLogAdapter.class.getSimpleName();
    public static final int FILTERING_ON_PRIORITY = 1;
    public static final int FILTERING_ON_TAG_KEYWORD = 2;
    private List<VLogModel> mVLogList;
    private List<VLogModel> mPriorityVLogList;
    private List<VLogModel> mResultantVLogList;
    private VLogModel mExpandedModel = null;
    private IVLogPriorityFilterListener mIVLogPriorityFilterListener;
    private int mFilteringOn = FILTERING_ON_PRIORITY;
    private PriorityFilter mPriorityFilter;
    private TagOrKeywordFilter mTagOrKeywordFilter;

    public VLogAdapter(List<VLogModel> vLogList) {
        this.mVLogList = vLogList;
        mPriorityVLogList = new ArrayList<>();
        mPriorityVLogList.addAll(mVLogList);
        mResultantVLogList = new ArrayList<>();
        mResultantVLogList.addAll(mPriorityVLogList);
    }

    @Override
    public VLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_log, parent, false));
    }

    @Override
    public void onBindViewHolder(final VLogViewHolder holder, final int position) {
        final VLogModel model = mResultantVLogList.get(position);

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

    public void setVLogList(List<VLogModel> vLogModels) {
        mVLogList = vLogModels;
        notifyDataSetChanged();
    }

    public void setFilteringOn(int filteringOn) {
        mFilteringOn = filteringOn;
    }

    @Override
    public int getItemCount() {
        return mResultantVLogList != null ? mResultantVLogList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        if (mFilteringOn == FILTERING_ON_PRIORITY) {
            if (mPriorityFilter == null) {
                mPriorityFilter = new PriorityFilter();
            }
            return mPriorityFilter;
        } else if (mFilteringOn == FILTERING_ON_TAG_KEYWORD) {
            if (mTagOrKeywordFilter == null) {
                mTagOrKeywordFilter = new TagOrKeywordFilter();
            }
            return mTagOrKeywordFilter;
        }
        return null;
    }

    public void setPriorityFilterListener(IVLogPriorityFilterListener vLogPriorityFilterListener) {
        mIVLogPriorityFilterListener = vLogPriorityFilterListener;
    }

    public void clearLogs() {
        mVLogList.clear();
        mPriorityVLogList.clear();
        mResultantVLogList.clear();
        notifyDataSetChanged();
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

    public void addLog(VLogModel model) {
        // TODO: add this log and refresh the list
        mVLogList.add(model);
        mPriorityVLogList.add(model);
        mResultantVLogList.add(model);
        notifyDataSetChanged();
    }

    private class PriorityFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mPriorityVLogList.clear();
            mResultantVLogList.clear();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                mPriorityVLogList.addAll(mVLogList);
                results.values = mPriorityVLogList;
                results.count = mPriorityVLogList.size();
            } else {
                int priority = Integer.parseInt(constraint.toString()) + 2;
                for (VLogModel item : mVLogList) {
                    if (item != null && item.getLogPriority() != VLogModel.UNKNOWN) {
                        if (item.getLogPriority() >= priority) {
                            mPriorityVLogList.add(item);
                        }
                    }
                }
                results.values = mPriorityVLogList;
                results.count = mPriorityVLogList.size();
            }
            mResultantVLogList.addAll(mPriorityVLogList);
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (mIVLogPriorityFilterListener != null) {
                if (results.count > 0) {
                    mIVLogPriorityFilterListener.updateFilteredDataOnUI(false);
                } else {
                    mIVLogPriorityFilterListener.updateFilteredDataOnUI(true);
                }
            }
            notifyDataSetChanged();
        }
    }

    private class TagOrKeywordFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mResultantVLogList.clear();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                mResultantVLogList.addAll(mPriorityVLogList);
                results.values = mResultantVLogList;
                results.count = mResultantVLogList.size();
            } else {
                for (VLogModel item : mPriorityVLogList) {
                    if (item != null && item.getLogMessage() != null) {
                        //if image title name starts with constraint, add it to filtered list
                        if (item.getLogMessage().toLowerCase().trim().contains(
                                constraint.toString().toLowerCase().trim())
                                || item.getTag().toLowerCase().trim().contains(
                                constraint.toString().toLowerCase().trim())) {
                            mResultantVLogList.add(item);
                        }
                    }
                }
                results.values = mResultantVLogList;
                results.count = mResultantVLogList.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (mIVLogPriorityFilterListener != null) {
                if (results.count > 0) {
                    mIVLogPriorityFilterListener.updateFilteredDataOnUI(false);
                } else {
                    mIVLogPriorityFilterListener.updateFilteredDataOnUI(true);
                }
            }
            notifyDataSetChanged();
        }
    }

    public interface IVLogPriorityFilterListener {
        void updateFilteredDataOnUI(boolean show);
    }
}