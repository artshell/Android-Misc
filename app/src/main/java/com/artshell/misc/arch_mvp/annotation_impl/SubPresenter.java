package com.artshell.misc.arch_mvp.annotation_impl;

import com.arch.mvp.BasePresenter;

/**
 * @author artshell on 2018/5/1
 */
public class SubPresenter extends BasePresenter<SubFragmentContract.View> implements SubFragmentContract.Presenter {

    @Override
    public void doSomeWork() {
        if (isViewAttached()) {
            getView().showSomething();
        }
    }

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        doSomeWork();
    }
}
