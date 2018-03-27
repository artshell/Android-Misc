package com.artshell.arch.storage;

import android.arch.lifecycle.LiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author artshell on 20/03/2018
 */

public class MainLiveDataStreams {

    @NonNull
    public static <T> LiveData<Result<T>> fromPublisher(@NonNull Publisher<T> publisher) {
        return new MainPublisherLiveData<>(publisher);
    }

    private static class MainPublisherLiveData<T> extends LiveData<Result<T>> {
        private final Publisher<T> publisher;

        private volatile MainSubscriber subscriber;
        @SuppressWarnings("rawtypes")
        private static final AtomicReferenceFieldUpdater<MainPublisherLiveData, MainSubscriber> WIP =
                AtomicReferenceFieldUpdater.newUpdater(MainPublisherLiveData.class, MainSubscriber.class, "subscriber");

        MainPublisherLiveData(Publisher<T> publisher) {
            this.publisher = publisher;
        }

        @Override
        protected void onActive() {
            super.onActive();
            MainSubscriber<T> s = new MainSubscriber<>(this);
            WIP.lazySet(this, s);
            publisher.subscribe(s);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            MainSubscriber s = WIP.getAndSet(this, null);
            if (s != null) {
                s.cancelSubscription();
            }
        }

        static final class MainSubscriber<T> implements Subscriber<T> {

            private MainPublisherLiveData<T> mainData;

            private volatile Subscription sub;
            @SuppressWarnings("rawtypes")
            private static final AtomicReferenceFieldUpdater<MainSubscriber, Subscription> UPDATER =
                    AtomicReferenceFieldUpdater.newUpdater(MainSubscriber.class, Subscription.class, "sub");

            MainSubscriber(MainPublisherLiveData<T> mainData) {
                this.mainData = mainData;
            }

            @Override
            public void onSubscribe(Subscription s) {
                if (UPDATER.compareAndSet(this, null, s)) {
                    post(Result.loading());
                    s.request(Long.MAX_VALUE);
                } else {
                    s.cancel();
                }
            }

            @Override
            public void onNext(T t) {
                post(Result.success(t));
            }

            @Override
            public void onError(Throwable thr) {
                post(Result.error(thr));
            }

            @Override
            public void onComplete() {
                post(Result.complete());
                UPDATER.lazySet(this, null);
            }

            void cancelSubscription() {
                Subscription s = UPDATER.get(this);
                if (s != null) {
                    s.cancel();
                }
            }

            void post(final Result<T> item) {
                if (Holder.isMainThread()) {
                    mainData.postValue(item);
                } else {
                    Holder.getInstance().post(() -> mainData.postValue(item));
                }
            }
        }

        private static class Holder {

            private static Handler getInstance() {
                return Singleton.instance;
            }

            private static boolean isMainThread() {
                return Looper.getMainLooper().getThread() == Thread.currentThread();
            }

            private static class Singleton {
                private static Handler instance = new Handler(Looper.getMainLooper());
            }
        }
    }
}
