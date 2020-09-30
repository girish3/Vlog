package com.android.girish.vlog.chatheads.chatheads;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class VLog {

    private static VLog vlog;
    private Context mApplicationContext;
    private boolean isEnabled = false;
    private Intent mServiceIntent;
    private int total = 0;
    private int MAX = 1000;
    private OverlayService mService;
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

        OverlayService.instance.updateNotification();
        if (total > MAX) {
            return;
        }

        total++;
        OverlayService.instance.addLog(model);
    }

    public void showBubble() {
        isEnabled = true;
        if (OverlayService.instance != null) {
            OverlayService.instance.addChat();
        }
    }

    public void hideBubble() {
        isEnabled = false;
        // hide bubble
    }

    void startService() {
        mServiceIntent = new Intent(mApplicationContext, OverlayService.class);
        // TODO: is there a need to pass token as an extra?
        mApplicationContext.bindService(mServiceIntent, mServerConn, Context.BIND_AUTO_CREATE);
        mApplicationContext.startService(mServiceIntent);
    }
}
