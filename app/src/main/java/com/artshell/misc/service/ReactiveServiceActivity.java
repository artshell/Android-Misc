package com.artshell.misc.service;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
    private ReactiveLiveDataViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_rx_live_service);

        model = ViewModelProviders.of(this).get(ReactiveLiveDataViewModel.class);

        findViewById(R.id.btn_obtain).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(v -> {
            if (model != null) {
                // 解绑, 验证"延时10秒的任务"是否能收到结果
                model.onCleared();
                model = null;
            }
        });


    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        // 延时10秒
        Bundle reactArgs = new Bundle();
        reactArgs.putString(ReactiveLiveDataService.PARAMETER_KEY, "React");
        model.getPublisherSource(reactArgs)
                .subscribe(result -> Log.i(TAG, "PublisherSource: " + Arrays.toString(result)));

        // 延时5秒
        Bundle liveArgs = new Bundle();
        liveArgs.putString(ReactiveLiveDataService.PARAMETER_KEY, "Live");
        model.getLiveSource(liveArgs)
                .observe(ReactiveServiceActivity.this, items -> Log.i(TAG, "LiveSource: " + items));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 置空ServiceConnection防止泄露
        model = null;
    }
}
