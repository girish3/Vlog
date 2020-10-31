package com.android.girish.vlog.chatheads.chatheads;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

public class VlogModel {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VERBOSE, DEBUG, INFO, WARN, ERROR})
    public @interface LogPriority {}

    /**
     * Priority constants
     */
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;

    private int mLogPriority;
    private String mTag;
    private String mLogMessage;


    public VlogModel(@LogPriority int priority, String tag, String logMessage) {
        mLogPriority = priority;
        mTag = tag;
        mLogMessage = logMessage;
    }

    public int getLogPriority() {
        return mLogPriority;
    }

    public void setLogPriority(int mLogPriority) {
        this.mLogPriority = mLogPriority;
    }

    @NonNull
    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    @NonNull
    public String getLogMessage() {
        return mLogMessage;
    }

    public void setLogMessage(String mLogMessage) {
        this.mLogMessage = mLogMessage;
    }
}
