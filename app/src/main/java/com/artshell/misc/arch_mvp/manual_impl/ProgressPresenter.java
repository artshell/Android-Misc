package com.artshell.misc.arch_mvp.manual_impl;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arch.mvp.BasePresenter;

/**
 * @author artshell on 2018/5/1
 */
public class ProgressPresenter extends BasePresenter<ProgressContract.View> implements ProgressContract.Presenter {

    private static final String TAG = "ProgressPresenter";

    private static final String PROGRESS_BAR_PERCENT_KEY = "progress_bar_percent_key";
    private static final String PROGRESS_BAR_STATE_KEY   = "progress_bar_state_key";

    private Bundle stateBundle = getStateBundle();
    private long progressTime;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void requestAfterDelay(long delay) {
        progressTime = delay;

        if (isViewAttached()) {
            getView().showProgress();
            stateBundle.putBoolean(PROGRESS_BAR_STATE_KEY, true);
        }

        handler.postDelayed(() -> {
            if (isViewAttached()) {
                getView().hideProgress();
                stateBundle.putBoolean(PROGRESS_BAR_STATE_KEY, false);
                stateBundle.putInt(PROGRESS_BAR_PERCENT_KEY, 0);
            }
        }, delay);

        updateProgress();
    }

    private void updateProgress() {
        handler.postDelayed(() -> {
            if (isViewAttached()) {
                int percent = stateBundle.getInt(PROGRESS_BAR_PERCENT_KEY);
                stateBundle.putInt(PROGRESS_BAR_PERCENT_KEY, ++percent);
                getView().updateProgress(percent);
                if (stateBundle.getBoolean(PROGRESS_BAR_STATE_KEY)) {
                    updateProgress();
                } else {
                    stateBundle.putInt(PROGRESS_BAR_PERCENT_KEY, 0);
                }
            }
        }, progressTime / 100);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        if (stateBundle.getBoolean(PROGRESS_BAR_STATE_KEY)) {
            if (isViewAttached()) {
                getView().showProgress();
            }
        }
    }

    @Override
    public void onPresenterDestroy() {
        super.onPresenterDestroy();
        Log.d(TAG, "Presenter destroyed");
    }
}
