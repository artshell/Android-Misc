package com.artshell.arch.utils;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author artshell on 20/03/2017
 */

public class RxSchedulers {

    /**
     * io线程切换到main线程
     * @param <U>
     * @return
     */
    public static <U> IoFuseTransformer<U> ioToMain() {
        return new IoFuseTransformer<>();
    }

    public static final class IoFuseTransformer<U> implements ObservableTransformer<U, U>, FlowableTransformer<U, U>,
            MaybeTransformer<U, U>, SingleTransformer<U, U>, CompletableTransformer {

        @Override
        public CompletableSource apply(Completable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Publisher<U> apply(Flowable<U> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public MaybeSource<U> apply(Maybe<U> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public ObservableSource<U> apply(Observable<U> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public SingleSource<U> apply(Single<U> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * trampoline线程切换到main线程
     * @param <U>
     * @return
     */
    public static <U> TrampolineFuseTransformer<U> trampolineToMain() {
        return new TrampolineFuseTransformer<>();
    }

    public static final class TrampolineFuseTransformer<U> implements ObservableTransformer<U, U>, FlowableTransformer<U, U>,
            MaybeTransformer<U, U>, SingleTransformer<U, U>, CompletableTransformer {
        @Override
        public CompletableSource apply(Completable upstream) {
            return upstream.subscribeOn(Schedulers.trampoline())
                    .unsubscribeOn(Schedulers.trampoline())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Publisher<U> apply(Flowable<U> upstream) {
            return upstream.subscribeOn(Schedulers.trampoline())
                    .unsubscribeOn(Schedulers.trampoline())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public MaybeSource<U> apply(Maybe<U> upstream) {
            return upstream.subscribeOn(Schedulers.trampoline())
                    .unsubscribeOn(Schedulers.trampoline())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public ObservableSource<U> apply(Observable<U> upstream) {
            return upstream.subscribeOn(Schedulers.trampoline())
                    .unsubscribeOn(Schedulers.trampoline())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public SingleSource<U> apply(Single<U> upstream) {
            return upstream.subscribeOn(Schedulers.trampoline())
                    .unsubscribeOn(Schedulers.trampoline())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * computation线程切换到main线程
     * @param <U>
     * @return
     */
    public static <U> ComputationFuseTransformer<U> computationToMain() {
        return new ComputationFuseTransformer<>();
    }

    public static final class ComputationFuseTransformer<U> implements ObservableTransformer<U, U>, FlowableTransformer<U, U>,
            MaybeTransformer<U, U>, SingleTransformer<U, U>, CompletableTransformer {
        @Override
        public CompletableSource apply(Completable upstream) {
            return upstream.subscribeOn(Schedulers.computation())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Publisher<U> apply(Flowable<U> upstream) {
            return upstream.subscribeOn(Schedulers.computation())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public MaybeSource<U> apply(Maybe<U> upstream) {
            return upstream.subscribeOn(Schedulers.computation())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public ObservableSource<U> apply(Observable<U> upstream) {
            return upstream.subscribeOn(Schedulers.computation())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public SingleSource<U> apply(Single<U> upstream) {
            return upstream.subscribeOn(Schedulers.computation())
                    .unsubscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * new线程切换到main线程
     * @param <U>
     * @return
     */
    public static <U> NewFuseTransformer<U> newToMain() {
        return new NewFuseTransformer<>();
    }

    public static final class NewFuseTransformer<U> implements ObservableTransformer<U, U>, FlowableTransformer<U, U>,
            MaybeTransformer<U, U>, SingleTransformer<U, U>, CompletableTransformer {
        @Override
        public CompletableSource apply(Completable upstream) {
            return upstream.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Publisher<U> apply(Flowable<U> upstream) {
            return upstream.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public MaybeSource<U> apply(Maybe<U> upstream) {
            return upstream.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public ObservableSource<U> apply(Observable<U> upstream) {
            return upstream.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public SingleSource<U> apply(Single<U> upstream) {
            return upstream.subscribeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * Single线程切换到main线程
     * @param <U>
     * @return
     */
    public static <U> SingleFuseTransformer<U> singleToMain() {
        return new SingleFuseTransformer<>();
    }

    public static final class SingleFuseTransformer<U> implements ObservableTransformer<U, U>, FlowableTransformer<U, U>,
            MaybeTransformer<U, U>, SingleTransformer<U, U>, CompletableTransformer {
        @Override
        public CompletableSource apply(Completable upstream) {
            return upstream.subscribeOn(Schedulers.single())
                    .unsubscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Publisher<U> apply(Flowable<U> upstream) {
            return upstream.subscribeOn(Schedulers.single())
                    .unsubscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public MaybeSource<U> apply(Maybe<U> upstream) {
            return upstream.subscribeOn(Schedulers.single())
                    .unsubscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public ObservableSource<U> apply(Observable<U> upstream) {
            return upstream.subscribeOn(Schedulers.single())
                    .unsubscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public SingleSource<U> apply(Single<U> upstream) {
            return upstream.subscribeOn(Schedulers.single())
                    .unsubscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * main线程切换到main线程
     * @param <U>
     * @return
     */
    public static <U> MainFuseTransformer<U> mainToMain() {
        return new MainFuseTransformer<>();
    }

    public static final class MainFuseTransformer<U> implements ObservableTransformer<U, U>, FlowableTransformer<U, U>,
            MaybeTransformer<U, U>, SingleTransformer<U, U>, CompletableTransformer {
        @Override
        public CompletableSource apply(Completable upstream) {
            return upstream.subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Publisher<U> apply(Flowable<U> upstream) {
            return upstream.subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public MaybeSource<U> apply(Maybe<U> upstream) {
            return upstream.subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public ObservableSource<U> apply(Observable<U> upstream) {
            return upstream.subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public SingleSource<U> apply(Single<U> upstream) {
            return upstream.subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
