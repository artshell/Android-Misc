package com.artshell.misc.arch_mvp.reactive_stream_impl;

import com.luseen.arch.BaseContract;
import com.luseen.arch.BaseLoadingContract;

/**
 * @author artshell on 2018/5/1
 */
public interface RxContract {
    interface View extends BaseLoadingContract.View {
        void onNext(String value);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void requestAfterDelay(long delay);
    }
}
