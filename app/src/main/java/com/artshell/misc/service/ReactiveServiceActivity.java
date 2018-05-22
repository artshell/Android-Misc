package com.artshell.misc.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.artshell.misc.R;

import java.util.Arrays;

/**
 * @author artshell on 2018/5/20
 */
public class ReactiveServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ReactiveServiceActivity";
    private ServiceConnection conn;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_rx_live_service);

        findViewById(R.id.btn_obtain).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(v -> {
            if (conn != null) {
                // 解绑, 验证"延时10秒的任务"是否能收到结果
                unbindService(conn);
                conn = null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        conn = new ServiceConnection() {

            @SuppressLint("CheckResult")
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                RxLiveDataService.SourceBinder binder = (RxLiveDataService.SourceBinder) service;

                // 延时10秒
                Bundle reactArgs = new Bundle();
                reactArgs.putString(RxLiveDataService.PARAMETER_KEY, "React");
                binder.getPublisherSource(reactArgs)
                        .subscribe(result -> Log.i(TAG, "PublisherSource: " + Arrays.toString(result)));

                // 延时5秒
                Bundle liveArgs = new Bundle();
                liveArgs.putString(RxLiveDataService.PARAMETER_KEY, "Live");
                binder.getLiveSource(liveArgs)
                        .observe(ReactiveServiceActivity.this, items -> Log.i(TAG, "LiveSource: " + items));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent tent = new Intent(this, RxLiveDataService.class);
        bindService(tent, conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
    }
}
