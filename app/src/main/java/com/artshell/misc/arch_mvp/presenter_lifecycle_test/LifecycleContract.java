package com.artshell.misc.arch_mvp.presenter_lifecycle_test;

import com.arch.mvp.BaseContract;

public interface LifecycleContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter<LifecycleContract.View> {
    }
}
