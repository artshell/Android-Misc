package com.artshell.arch.storage;

import android.arch.lifecycle.MediatorLiveData;

import io.reactivex.internal.subscribers.LambdaSubscriber;

/**
 * @author artshell on 19/03/2018
 */

public class Subscribers {
    public static <T> LambdaSubscriber<T> create(MediatorLiveData<Result<T>> resultData) {
        return new LambdaSubscriber<>(
                t -> resultData.setValue(Result.success(t)),
                thr -> resultData.setValue(Result.error(thr)),
                () -> resultData.setValue(Result.complete()),
                s -> resultData.setValue(Result.loading()));
    }
}
