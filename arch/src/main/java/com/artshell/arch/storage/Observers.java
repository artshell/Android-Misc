package com.artshell.arch.storage;

import android.arch.lifecycle.MediatorLiveData;

import io.reactivex.internal.observers.LambdaObserver;

/**
 * @author artshell on 19/03/2018
 */

public class Observers {
    public static <T> LambdaObserver<T> create(MediatorLiveData<Result<T>> resultData) {
        return new LambdaObserver<>(
                t -> resultData.setValue(Result.success(t)),
                thr -> resultData.setValue(Result.error(thr)),
                () -> resultData.setValue(Result.complete()),
                s -> resultData.setValue(Result.loading()));
    }
}
