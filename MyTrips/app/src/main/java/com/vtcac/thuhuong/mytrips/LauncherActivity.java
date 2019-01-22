package com.vtcac.thuhuong.mytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vtcac.thuhuong.mytrips.base.MyApplication;

public class LauncherActivity extends AppCompatActivity {
    private final static int SPLASH_DISPLAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (((MyApplication) getApplication()).getTravelListSize() > 0) {
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    finish();
                    return;
                }
                startActivity(new Intent(LauncherActivity.this, WelcomeActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
