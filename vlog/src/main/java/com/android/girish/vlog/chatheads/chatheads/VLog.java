package com.android.girish.vlog.chatheads.chatheads;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android.girish.vlog.chatheads.chatheads.filter.VlogRepository;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class VLog {

    private static final String TAG = VLog.class.getSimpleName();

    private static VLog vlog;
    private Context mApplicationContext;
    private AtomicBoolean isEnabled = new AtomicBoolean(false);
    private Intent mServiceIntent;
    private int total = 0;
    private int MAX = 1000;
    private VlogService mService;
    private VlogRepository mVlogRepository;
    private AtomicBoolean mBound = new AtomicBoolean(false);

    ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            VlogService.LocalBinder service = (VlogService.LocalBinder) binder;
            mService = service.getService();
            mBound.set(true);
            Log.d(TAG, "Service connected");
            if (isEnabled.get() && mBound.get()) {
                Log.d(TAG, "Displaying Vlog Bubble");
                mService.addChat();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service disconnected");
            mService = null;
            mBound.set(false);
        }
    };

    public static VLog getInstance() {
        // TODO: make it thread safe.
        if (vlog == null) {
            vlog = new VLog();
        }
        return vlog;

    }

    public boolean isEnabled() {
        return isEnabled.get();
    }

    private VLog() {
        // TODO: use DI, isolating the dependency for now
        injectFilterManager();
    }

    private void injectFilterManager() {
        mVlogRepository = new VlogRepository();
    }

    public void start(Context context) {
        // Ignore if already started
        if (isEnabled.getAndSet(true)) {
            Log.d(TAG, "Vlog is already started");
            return;
        }

        Log.d(TAG, "Initializing Vlog");
        mApplicationContext = context;
        startService();

        // initialize other resources if any
    }

    private boolean allowLogging() {
        return isEnabled.get() && VlogService.sInstance != null;
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
        isEnabled.set(true);
        if (VlogService.sInstance != null) {
            VlogService.sInstance.addChat();
        }
    }

    public void stop() {
        if (!isEnabled.get()) {
            Log.d(TAG, "Vlog is not started");
            return;
        }

        Log.d(TAG, "Stopping Vlog");

        isEnabled.set(false);
        if (mServiceIntent != null) {
            mService.cleanUp();
            mApplicationContext.unbindService(mServerConn);
            mApplicationContext.stopService(mServiceIntent);
            mServiceIntent = null;
        }
    }

    private void startService() {
        mServiceIntent = new Intent(mApplicationContext, VlogService.class);
        // TODO: is there a need to pass token as an extra?
        mApplicationContext.bindService(mServiceIntent, mServerConn, Context.BIND_AUTO_CREATE);
        mApplicationContext.startService(mServiceIntent);
    }

    @NotNull
    VlogRepository getVlogRepository() {
        return mVlogRepository;
    }
}
