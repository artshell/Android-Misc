package com.artshell.misc.arch_mvp.annotation_impl;

import com.arch.mvp.BaseContract;

/**
 * @author artshell on 2018/5/1
 */
public interface SubFragmentContract {
    interface View extends BaseContract.View {
        void showSomething();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void doSomeWork();
    }
}
