package com.android.girish.vlog.chatheads.chatheads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.girish.vlog.R;

import java.util.List;

public class VLogAdapter extends RecyclerView.Adapter<VLogAdapter.VLogViewHolder> implements Filterable {

    private static final String TAG = VLogAdapter.class.getSimpleName();
    public static final int FILTERING_ON_PRIORITY = 1;
    public static final int FILTERING_ON_TAG_KEYWORD = 2;
    private List<VLog> mVLogList;
    private List<VLog> mPriorityVLogList;
    private VLog mExpandedModel = null;
    private IVLogPriorityFilterListener mIVLogPriorityFilterListener;
    private int mFilteringOn = FILTERING_ON_PRIORITY;
    private PriorityFilter mPriorityFilter;
    private TagOrKeywordFilter mTagOrKeywordFilter;

    public VLogAdapter(List<VLog> vLogList) {
        this.mVLogList = vLogList;
    }

    @Override
    public VLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_log, parent, false));
    }

    @Override
    public void onBindViewHolder(final VLogViewHolder holder, final int position) {
        final VLog model = mVLogList.get(position);

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
            case VLog.ASSERT:
                return "A";
            case VLog.DEBUG:
                return "D";
            case VLog.ERROR:
                return "E";
            case VLog.INFO:
                return "I";
            case VLog.VERBOSE:
                return "V";
            case VLog.WARN:
                return "W";
            default:
                return "";
        }
    }

    public void setVLogList(List<VLog> vLogModels) {
        mVLogList = vLogModels;
        notifyDataSetChanged();
    }

    public void setFilteringOn(int filteringOn) {
        mFilteringOn = filteringOn;
    }

    @Override
    public int getItemCount() {
        return mVLogList != null ? mVLogList.size() : 0;
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


    private class PriorityFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mPriorityVLogList.clear();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                mPriorityVLogList.clear();
                mPriorityVLogList.addAll(mVLogList);
                results.values = mPriorityVLogList;
                results.count = mPriorityVLogList.size();
            } else {
                int priority = Integer.parseInt(constraint.toString());
                for (VLog item : mVLogList) {
                    if (item != null && item.getLogPriority() != VLog.UNKNOWN) {
                        if (item.getLogPriority() == priority) {
                            mPriorityVLogList.add(item);
                        }
                    }
                }
                results.values = mPriorityVLogList;
                results.count = mPriorityVLogList.size();
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

    private class TagOrKeywordFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mPriorityVLogList.clear();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                mPriorityVLogList.clear();
                mPriorityVLogList.addAll(mVLogList);
                results.values = mPriorityVLogList;
                results.count = mPriorityVLogList.size();
            } else {
                for (VLog item : mVLogList) {
                    if (item != null && item.getLogMessage() != null) {
                        //if image title name starts with constraint, add it to filtered list
                        if (item.getLogMessage().toLowerCase().trim().contains(
                                constraint.toString().toLowerCase().trim())) {
                            mPriorityVLogList.add(item);
                        }
                    }
                }
                results.values = mPriorityVLogList;
                results.count = mPriorityVLogList.size();
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