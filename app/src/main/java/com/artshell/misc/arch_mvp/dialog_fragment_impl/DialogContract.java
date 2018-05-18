package com.artshell.misc.arch_mvp.dialog_fragment_impl;

import com.arch.mvp.BaseContract;

import java.util.List;

public interface DialogContract {
    interface View extends BaseContract.View {
        void deliveryResult(List<String> result);
    }

    interface Presenter extends BaseContract.Presenter<DialogContract.View> {
        void requestServer();
    }
}
