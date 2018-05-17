package com.artshell.misc.arch_mvp.reactive_stream_impl;

import com.arch.mvp.BaseContract;
import com.arch.mvp.BaseLoadingContract;

/**
 * @author artshell on 2018/5/1
 */
public interface ReactiveContract {
    interface View extends BaseLoadingContract.View {
        void onNext(String value);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void requestAfterDelay(long delay);
    }
}
