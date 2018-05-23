package com.artshell.misc.service;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleService;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * @author artshell on 2018/5/21
 */
public class ReactiveLifeService extends LifecycleService implements
        DefaultLifecycleObserver, LifecycleProvider<Lifecycle.Event>,
        Function<Lifecycle.Event, Lifecycle.Event> {

    private final Subject<Lifecycle.Event> serviceCycle = BehaviorSubject.<Lifecycle.Event>create().toSerialized();

    @CallSuper
    @Override
    public void onCreate() {
        getLifecycle().addObserver(this);
        super.onCreate();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        serviceCycle.onNext(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        serviceCycle.onNext(Lifecycle.Event.ON_START);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        serviceCycle.onNext(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        serviceCycle.onNext(Lifecycle.Event.ON_DESTROY);
    }

    @Nonnull
    @Override
    public Observable<Lifecycle.Event> lifecycle() {
        return serviceCycle.hide();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull Lifecycle.Event event) {
        return RxLifecycle.bindUntilEvent(serviceCycle, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bind(serviceCycle, this);
    }

    @Override
    public Lifecycle.Event apply(Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                return Lifecycle.Event.ON_DESTROY;
            case ON_START:
                return Lifecycle.Event.ON_STOP;
            case ON_PAUSE:
                return Lifecycle.Event.ON_STOP;
            case ON_STOP:
                return Lifecycle.Event.ON_DESTROY;
            case ON_DESTROY:
                throw new OutsideLifecycleException("Cannot bind to LifecycleOwner's lifecycle when outside of it.");
            default:
                throw new UnsupportedOperationException("Binding to " + event + " not yet implemented");
        }
    }
}
