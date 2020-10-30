package com.android.girish.vlog.chatheads.chatheads;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.android.girish.vlog.chatheads.chatheads.filter.VlogRepository;

import org.jetbrains.annotations.NotNull;

public class VLog {

    private static VLog vlog;
    private Context mApplicationContext;
    private boolean isEnabled = false;
    private Intent mServiceIntent;
    private int total = 0;
    private int MAX = 1000;
    private OverlayService mService;
    private VlogRepository mVlogRepository;
    private boolean mBound;

    ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            OverlayService.LocalBinder service = (OverlayService.LocalBinder) binder;
            mService = service.getService();
            mBound = true;
            if (isEnabled) {
                mService.addChat();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    public static VLog getInstance() {
        // TODO: make it thread safe.
        if (vlog == null) {
            vlog = new VLog();
        }
        return vlog;

    }

    private VLog() {
        // TODO: use DI, isolating the dependency for now
        injectFilterManager();
    }

    private void injectFilterManager() {
        mVlogRepository = new VlogRepository();
    }

    public void initialize(Context context) {
        mApplicationContext = context;
        if (mServiceIntent != null) {
            mApplicationContext.stopService(mServiceIntent);
        }
        startService();

        // initialize other resources if any
    }

    private boolean allowLogging() {
        return isEnabled && OverlayService.instance != null;
    }

    public void feed(VLogModel model) {
        if (!allowLogging()) return;

        if (total > MAX) {
            return;
        }

        total++;
        mVlogRepository.feedLog(model);
    }

    public void showBubble() {
        isEnabled = true;
        if (OverlayService.instance != null) {
            OverlayService.instance.addChat();
        }
    }

    private void hideBubble() {
        isEnabled = false;
        // hide bubble
    }

    void startService() {
        mServiceIntent = new Intent(mApplicationContext, OverlayService.class);
        // TODO: is there a need to pass token as an extra?
        mApplicationContext.bindService(mServiceIntent, mServerConn, Context.BIND_AUTO_CREATE);
        mApplicationContext.startService(mServiceIntent);
    }

    @NotNull
    public VlogRepository getVlogRepository() {
        return mVlogRepository;
    }
}
