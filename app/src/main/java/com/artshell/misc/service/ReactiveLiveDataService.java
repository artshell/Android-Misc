package com.artshell.misc.service;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author artshell on 2018/5/21
 */
public class ReactiveLiveDataService extends ReactiveLifeService {
    private static final String TAG = "ReactiveLiveDataService";

    public static final String PARAMETER_KEY = "parameter_key";

    private volatile Looper  mLooper;
    private volatile Handler mHandler;

    private MutableLiveData<List<String>> mLiveSource = new MutableLiveData<>();
    private Subject<String[]>             mPublisher  = PublishSubject.<String[]>create().toSerialized();
    private Observable<String[]>          mRealSource = mPublisher.compose(bindUntilEvent(Lifecycle.Event.ON_PAUSE));
    private SourceBinder                  mBinder     = new SourceBinder();

    public class SourceBinder extends Binder {
        public ReactiveLiveDataService getService() {
            return ReactiveLiveDataService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ReactiveLiveDataService");
        thread.start();

        mLooper = thread.getLooper();
        mHandler = new Handler(mLooper);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLooper != null) {
            mLooper.quit();
        }
    }

    /**
     * 延时5秒
     * @param args
     * @return
     */
    LiveData<List<String>> getLiveSource(Bundle args) {
        String key = args.getString(PARAMETER_KEY);
        mHandler.postDelayed(() -> {
            List<String> asList = Arrays.asList("liveData-" + key, "lifecycle-" + key, "Room-" + key, "Paging-" + key);
            mLiveSource.postValue(asList);
        }, 5000);
        return mLiveSource;
    }


    /**
     * 延时10秒
     * @param args
     * @return
     */
    @SuppressLint("CheckResult")
    Observable<String[]> getPublisherSource(Bundle args) {
        String key = args.getString(PARAMETER_KEY);
        Observable.fromArray("A", "B", "C", "D", "E", "F")
                .collectInto(new ArrayList<String>(), (list, item) -> list.add(item + "-" + key))
                .delay(10000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arr -> mPublisher.onNext(arr.toArray(new String[arr.size()])));
        return mRealSource;
    }
}
