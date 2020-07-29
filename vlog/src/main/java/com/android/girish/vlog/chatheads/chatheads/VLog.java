package com.android.girish.vlog.chatheads.chatheads;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class Vlog {

    private static Vlog vlog;
    private Context mApplicationContext;
    private boolean isEnabled = false;

    public static Vlog getInstance() {
        // TODO: make it thread safe.
        if (vlog == null) {
            vlog = new Vlog();
        }
        return vlog;
    }

    private Vlog() {
    }

    public void initialize(Context context) {
        mApplicationContext = context;
        startService();

        // initialize other resources if any
    }

    public void feed(VLogModel model) {
        if (!isEnabled) return;

        OverlayService.instance.addLog(model);
    }

    public void showBubble() {
        isEnabled = true;
        OverlayService.instance.addChat();
    }

    public void hideBubble() {
        isEnabled = false;
        // hide bubble
    }

    void startService() {
        Intent intent = new Intent(mApplicationContext, OverlayService.class);
        // TODO: is there a need to pass token as an extra?
        mApplicationContext.startService(intent);
    }
}
