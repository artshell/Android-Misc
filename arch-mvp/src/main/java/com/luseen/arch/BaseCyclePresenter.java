package com.luseen.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author artshell on 2018/5/1
 */
public class BaseCyclePresenter<V extends BaseContract.View> extends BasePresenter<V>
        implements LifecycleProvider<Lifecycle.Event> {

    private final BehaviorSubject<Lifecycle.Event> cycleSubject = BehaviorSubject.create();

    @NonNull
    @CheckResult
    @Override
    public Observable<Lifecycle.Event> lifecycle() {
        return cycleSubject.hide();
    }

    @NonNull
    @CheckResult
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(Lifecycle.Event event) {
        return RxLifecycle.bindUntilEvent(cycleSubject, event);
    }

    @NonNull
    @CheckResult
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxArchCycle.bindLifecycle(cycleSubject);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    final void onEvent(LifecycleOwner owner, Lifecycle.Event event) {
        if (cycleSubject.hasObservers()) {
            cycleSubject.onNext(event);
        }
    }
}
