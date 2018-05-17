package com.artshell.misc.arch_mvp.annotation_impl;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.arch.mvp.BasePresenter;

/**
 * @author artshell on 2018/5/1
 */
public class MasterPresenter extends BasePresenter<MasterContract.View>
        implements MasterContract.Presenter {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        if (isViewAttached()) {
            getView().openSubFragment();
        }
    }

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
    }
}
