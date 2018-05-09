package com.luseen.arch;


import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

/**
 * If you use <b>Java 7 Language</b>, Lifecycle events are observed using annotations.
 * Once Java 8 Language becomes mainstream on Android, annotations will be deprecated, so between
 * {@link DefaultLifecycleObserver} and annotations, you must always prefer {@code DefaultLifecycleObserver}.
 *
 * If you use <b>Java 8 Language</b>, then observe events with {@link DefaultLifecycleObserver}.
 * To include it you should add {@code "android.arch.lifecycle:common-java8:<version>"} to your
 * build.gradle file.
 *
 * Created by Chatikyan on 20.05.2017.
 */
public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V>,
        DefaultLifecycleObserver {

    private Bundle stateBundle;
    private V view;

    @Override
    final public void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    final public void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    @Override
    final public void attachView(V view) {
        this.view = view;
    }

    @Override
    final public void detachView() {
        view = null;
    }

    @Override
    final public V getView() {
        return view;
    }

    @Override
    final public boolean isViewAttached() {
        return view != null;
    }

    @Override
    final public Bundle getStateBundle() {
        return stateBundle == null
                ? stateBundle = new Bundle()
                : stateBundle;
    }

    @Override
    public void onPresenterCreated() {
        //NO-OP
    }

    @CallSuper
    @Override
    public void onPresenterDestroy() {
        if (stateBundle != null && stateBundle.size() > 0) {
            stateBundle.clear();
        }
    }

    @CallSuper
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }
}
