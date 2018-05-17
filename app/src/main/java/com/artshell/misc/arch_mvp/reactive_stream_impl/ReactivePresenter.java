package com.artshell.misc.arch_mvp.reactive_stream_impl;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;

import com.arch.mvp.BaseCyclePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author artshell on 2018/5/1
 */
public class ReactivePresenter extends BaseCyclePresenter<ReactiveContract.View>
        implements ReactiveContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void requestAfterDelay(long delay) {
        Observable.<String>create(
                emitter -> {
                    for (int i = 0; i < 5 && !emitter.isDisposed(); i++) {
                        emitter.onNext("$" + i);
                    }
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }
                })
                .delay(delay, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .compose(bindUntilEvent(Lifecycle.Event.ON_PAUSE))
                .doOnSubscribe(dis -> getView().showLoading())
                .doFinally(() -> getView().hideLoading())
                .subscribe(value -> getView().onNext(value), thr -> getView().showError(thr.getMessage()));
    }
}
