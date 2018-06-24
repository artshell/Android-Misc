package com.artshell.misc.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.artshell.misc.R;

/**
 * Dynamic request orientation
 * <a href="https://developer.android.google.cn/guide/topics/manifest/activity-element#screen">android:screenOrientation</a>
 */
public class RequestOrientationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_orientation);
        setProperty();
    }

    private void setProperty() {
        findViewById(R.id.btn_portrait).setOnClickListener(this);
        findViewById(R.id.btn_landscape).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_portrait:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }
    }
}
