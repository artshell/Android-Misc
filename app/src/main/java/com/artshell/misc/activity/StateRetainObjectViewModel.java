package com.artshell.misc.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author artshell on 2018/6/12
 */
public class StateRetainObjectViewModel extends AndroidViewModel implements Runnable {
    private static final String TAG = "StateRetainObjectViewModel";

    private Handler                   mHandler       = new Handler(Looper.getMainLooper());
    private AtomicInteger             mAtomicInteger = new AtomicInteger(0);
    private MediatorLiveData<Integer> parentProgress = new MediatorLiveData<>();

    public StateRetainObjectViewModel(@NonNull Application application) {
        super(application);
        mAtomicInteger.set(0);
        parentProgress.setValue(mAtomicInteger.get());
    }

    public MediatorLiveData<Integer> getParentProgress() {
        return parentProgress;
    }

    public void startSimulatorTask() {
        if (parentProgress.getValue() == 100) return;
        mHandler.postDelayed(this, 1000);
    }

    @Override
    public void run() {
        if (mAtomicInteger.get() < 100) {
            mAtomicInteger.getAndIncrement();
            parentProgress.setValue(mAtomicInteger.get());
            mHandler.postDelayed(this, 1000);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: ");
    }
}
