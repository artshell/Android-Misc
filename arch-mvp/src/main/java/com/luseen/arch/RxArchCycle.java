package com.luseen.arch;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.trello.rxlifecycle2.RxLifecycle.bind;

/**
 * @author artshell on 2018/5/1
 */
public final class RxArchCycle {

    private RxArchCycle() {
        throw new RuntimeException("No instances for RxArchCycle");
    }

    @NonNull
    @CheckResult
    public static <T> LifecycleTransformer<T> bindLifecycle(@NonNull Observable<Lifecycle.Event> lifecycle) {
        return bind(lifecycle, LIFECYCLE);
    }

    private static final Function<Lifecycle.Event, Lifecycle.Event> LIFECYCLE = new Function<Lifecycle.Event, Lifecycle.Event>() {
        @Override
        public Lifecycle.Event apply(Lifecycle.Event lastEvent) throws Exception {
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
                    throw new OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
            }
        }
    };
}
