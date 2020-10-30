package com.android.girish.vlog.chatheads.chatheads;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

public class VlogModel implements Parcelable {

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

    protected VlogModel(Parcel in) {
        mLogPriority = in.readInt();
        mTag = in.readString();
        mLogMessage = in.readString();
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

    public static final Creator<VlogModel> CREATOR = new Creator<VlogModel>() {
        @Override
        public VlogModel createFromParcel(Parcel in) {
            return new VlogModel(in);
        }

        @Override
        public VlogModel[] newArray(int size) {
            return new VlogModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mLogPriority);
        dest.writeString(mTag);
        dest.writeString(mLogMessage);
    }
}
