package com.artshell.misc.arch_mvp.live_data_impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;

import com.luseen.arch.BasePresenter;

import java.util.Arrays;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class LiveDataPresenter extends BasePresenter<LiveDataContract.View>
        implements LiveDataContract.Presenter {

    @Override
    public LiveData<List<String>> getList() {
        return LiveDataReactiveStreams.fromPublisher(
                Flowable.create(emitter ->{
                    if (emitter.isCancelled()) {
                        return;
                    }
                    List<String> items = Arrays.asList("A", "Rust", "C", "D", "java");
                    if (!emitter.isCancelled()) {
                        emitter.onNext(items);
                    }
                }, BackpressureStrategy.BUFFER)
        );
    }
}
