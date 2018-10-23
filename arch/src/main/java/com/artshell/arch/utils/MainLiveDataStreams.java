package com.artshell.arch.utils;

import android.arch.lifecycle.LiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.Resource;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author artshell on 20/03/2018
 */

public class MainLiveDataStreams {

    @NonNull
    public static <T> LiveData<Resource<T>> fromPublisher(@NonNull Publisher<T> publisher) {
        return new MainPublisherLiveData<>(publisher);
    }

    private static class MainPublisherLiveData<T> extends LiveData<Resource<T>> {
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

            private Handler handler = new Handler(Looper.getMainLooper());

            MainSubscriber(MainPublisherLiveData<T> mainData) {
                this.mainData = mainData;
            }

            @Override
            public void onSubscribe(Subscription s) {
                if (UPDATER.compareAndSet(this, null, s)) {
                    post(Resource.loading());
                    s.request(Long.MAX_VALUE);
                } else {
                    s.cancel();
                    post(Resource.cancel());
                }
            }

            @Override
            public void onNext(T t) {
                post(Resource.success(t));
            }

            @Override
            public void onError(Throwable thr) {
                post(Resource.error(thr));
            }

            @Override
            public void onComplete() {
                UPDATER.lazySet(this, null);
                post(Resource.complete());
            }

            void cancelSubscription() {
                Subscription s = UPDATER.get(this);
                if (s != null) {
                    s.cancel();
                }
                post(Resource.cancel());
            }

            void post(final Resource<T> item) {
                if (isMainThread()) {
                    mainData.setValue(item);
                } else {
                    handler.postAtFrontOfQueue(() -> mainData.setValue(item));
                }
            }

            boolean isMainThread() {
                return Looper.getMainLooper().getThread() == Thread.currentThread();
            }
        }
    }
}
