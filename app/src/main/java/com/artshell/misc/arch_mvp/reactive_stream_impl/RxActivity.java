package com.artshell.misc.arch_mvp.reactive_stream_impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.artshell.misc.R;
import com.luseen.arch.BaseAnnotatedActivity;
import com.luseen.arch.Viewable;

/**
 * @author artshell on 2018/5/1
 */
@Viewable(presenter = RxPresenter.class, layout = R.layout.activity_reactive_stream)
public class RxActivity extends BaseAnnotatedActivity<RxContract.View, RxContract.Presenter>
        implements RxContract.View, View.OnClickListener {

    private static final String TAG = "RxActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.requestAfterDelay(1000);
    }

    @Override
    public void onNext(String value) {
        Log.i(TAG, "onNext: " + value);
    }

    @Override
    public void showLoading() {
        Log.i(TAG, "showLoading: ");
    }

    @Override
    public void hideLoading() {
        Log.i(TAG, "hideLoading: ");
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, message);
    }
}
