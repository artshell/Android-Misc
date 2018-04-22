package com.artshell.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import nucleus5.presenter.Presenter;
import nucleus5.view.NucleusFragmentActivity;

/**
 * @author artshell on 2018/4/22
 */
public class BaseLifecycleActivity<P extends Presenter> extends NucleusFragmentActivity<P> {

    private Subject<ActivityEvent> takeSubject;

    public BaseLifecycleActivity() {
        takeSubject = BehaviorSubject.<ActivityEvent>create().toSerialized();
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        takeSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        takeSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        takeSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        takeSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        takeSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        takeSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    public final <T> LifecycleTransformer<T> takeUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(takeSubject, event);
    }
}
