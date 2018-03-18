package com.artshell.arch.common;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import io.reactivex.Notification;

/**
 * @author artshell on 16/03/2018
 */

public class SimpleObserver<T> implements Observer<Notification<T>> {

    @Override
    public void onChanged(@Nullable Notification<T> notify) {
        if (notify != null) {
            if (notify.isOnNext()) {
                onNext(notify.getValue());
            } else if (notify.isOnError()){
                onError(notify.getError());
            } else if (notify.isOnComplete()) {
                onComplete();
            }
        }
    }

    protected void onNext(T t) {

    }

    protected void onError(Throwable thr) {

    }

    protected void onComplete() {

    }
}
