package com.artshell.misc.arch_mvp.dialog_fragment_impl;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;

import com.arch.mvp.BaseCyclePresenter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogPresenter extends BaseCyclePresenter<DialogContract.View>
        implements DialogContract.Presenter {

    @SuppressLint("CheckResult")
    @SuppressWarnings("unchecked")
    @Override
    public void requestServer() {
        Observable.fromArray(Arrays.asList("mvc", "mvvm", "mvp", "rust"))
                .delay(300, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .compose(bindUntilEvent(Lifecycle.Event.ON_PAUSE))
                .filter(items -> isViewAttached())
                .subscribe(items -> getView().deliveryResult(items), thr -> {});
    }
}
