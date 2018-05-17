package com.artshell.misc.arch_mvp.live_data_impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.artshell.misc.R;
import com.arch.mvp.BaseActivity;

public class LiveDataActivity extends BaseActivity<LiveDataContract.View, LiveDataContract.Presenter>
        implements LiveDataContract.View, View.OnClickListener {

    private static final String TAG = "LiveDataActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_live_data);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.getList().observe(this, items -> {
            if (items == null) {
                return;
            }

            for (int i = 0; i < items.size(); i++) {
                Log.i(TAG, "onClick: index -> " + i + ", elements =>" + items.get(i));
            }
        });
    }

    @Override
    protected LiveDataContract.Presenter initPresenter() {
        return new LiveDataPresenter();
    }
}
