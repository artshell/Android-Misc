package com.luseen.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Add Reactive lifecycle's features for {@link BasePresenter}
 * @param <V>
 *
 * @author artshell on 2018/5/6
 */
public class BaseCyclePresenter<V extends BaseContract.View> extends BasePresenter<V>
        implements LifecycleProvider<Lifecycle.Event>, Function<Lifecycle.Event, Lifecycle.Event> {

    private final BehaviorSubject<Lifecycle.Event> cycleSubject = BehaviorSubject.create();

    @Override
    public final void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        cycleSubject.onNext(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public final void onStart(@NonNull LifecycleOwner owner) {
        super.onStart(owner);
        cycleSubject.onNext(Lifecycle.Event.ON_START);
    }

    @Override
    public final void onResume(@NonNull LifecycleOwner owner) {
        super.onResume(owner);
        cycleSubject.onNext(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public final void onPause(@NonNull LifecycleOwner owner) {
        cycleSubject.onNext(Lifecycle.Event.ON_PAUSE);
        super.onPause(owner);
    }

    @Override
    public final void onStop(@NonNull LifecycleOwner owner) {
        cycleSubject.onNext(Lifecycle.Event.ON_STOP);
        super.onStop(owner);
    }

    @Override
    public final void onDestroy(@NonNull LifecycleOwner owner) {
        cycleSubject.onNext(Lifecycle.Event.ON_DESTROY);
        super.onDestroy(owner);
    }

    @NonNull
    @CheckResult
    @Override
    public final Observable<Lifecycle.Event> lifecycle() {
        return cycleSubject.hide();
    }

    @NonNull
    @CheckResult
    @Override
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull Lifecycle.Event event) {
        return RxLifecycle.bindUntilEvent(cycleSubject, event);
    }

    @NonNull
    @CheckResult
    @Override
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bind(cycleSubject, this);
    }

    @Override
    public final Lifecycle.Event apply(Lifecycle.Event lastEvent) throws Exception {
        switch (lastEvent) {
            case ON_CREATE:
                return Lifecycle.Event.ON_DESTROY;
            case ON_START:
                return Lifecycle.Event.ON_STOP;
            case ON_RESUME:
                return Lifecycle.Event.ON_PAUSE;
            case ON_PAUSE:
                return Lifecycle.Event.ON_STOP;
            case ON_STOP:
                return Lifecycle.Event.ON_DESTROY;
            case ON_DESTROY:
                throw new OutsideLifecycleException("Cannot bind to LifecycleOwner's lifecycle when outside of it.");
            default:
                throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
        }
    }
}
