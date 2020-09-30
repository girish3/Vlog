package com.girish.vlogsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.girish.vlog.chatheads.chatheads.GenreDataFactory;
import com.android.girish.vlog.chatheads.chatheads.OverlayService;
import com.android.girish.vlog.chatheads.chatheads.VLog;
import com.android.girish.vlog.chatheads.chatheads.VLogModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VLog mVlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageDrawOverOtherApps();

        mVlog = VLog.getInstance();
        mVlog.initialize(getApplicationContext());

        Button addBubble = findViewById(R.id.addBubble);
        final Button addFeed = findViewById(R.id.addFeed);

        addBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVlog.showBubble();
                //startActivity();
            }
        });

        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<VLogModel> vLogModels = getRandomLogs();
                for (VLogModel model : vLogModels) {
                    final VLogModel vLogModel = model;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mVlog.feed(vLogModel);
                        }
                    }, 80);
                }
            }
        });
    }

    private List<VLogModel> getRandomLogs() {
        List<VLogModel> logModelList = GenreDataFactory.generateLogs();
        return logModelList;
    }

    private void manageDrawOverOtherApps() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        int REQUEST_CODE = 5469;

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
