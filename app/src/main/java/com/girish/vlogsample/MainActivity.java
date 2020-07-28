package com.girish.vlogsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.android.girish.vlog.chatheads.chatheads.OverlayService;
import com.android.girish.vlog.chatheads.chatheads.expand.ExpandActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageDrawOverOtherApps();

        startService();

        Button addBubble = findViewById(R.id.addBubble);
        Button addNotification = findViewById(R.id.addNotification);

        addBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OverlayService.instance.addChat();
                //startActivity();
            }
        });

        addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OverlayService.instance.updateNotification();
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(this, ExpandActivity.class);
        startActivity(intent);
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

    void startService() {
        Intent intent = new Intent(this, OverlayService.class);
        // TODO: is there a need to pass token as an extra?
        startService(intent);
    }
}
