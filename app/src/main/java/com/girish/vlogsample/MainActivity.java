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
        //mVlog.start(getApplicationContext());

        Button startButton = findViewById(R.id.start);
        Button stopButton = findViewById(R.id.stop);
        final Button addFeed = findViewById(R.id.addFeed);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVlog.start(getApplicationContext());
                //startActivity();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVlog.stop();
            }
        });

        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<VLogModel> vLogModels = getRandomLogs();
                for (VLogModel model : vLogModels) {
                    if (mVlog.isEnabled()) {
                        mVlog.feed(model);
                    }
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
