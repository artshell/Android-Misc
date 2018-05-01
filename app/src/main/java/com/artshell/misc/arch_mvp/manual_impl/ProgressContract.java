package com.artshell.misc.arch_mvp.manual_impl;

import com.luseen.arch.BaseContract;

/**
 * @author artshell on 2018/5/1
 */
public interface ProgressContract {

    interface View extends BaseContract.View {

        void showProgress();

        void hideProgress();

        void updateProgress(int percent);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void requestAfterDelay(long delay);
    }
}
