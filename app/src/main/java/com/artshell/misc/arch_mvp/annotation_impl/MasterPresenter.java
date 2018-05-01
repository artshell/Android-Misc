package com.artshell.misc.arch_mvp.annotation_impl;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.luseen.arch.BasePresenter;

/**
 * @author artshell on 2018/5/1
 */
public class MasterPresenter extends BasePresenter<MasterContract.View>
        implements MasterContract.Presenter {

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        if (isViewAttached()) {
            getView().openSubFragment();
        }
    }
}
