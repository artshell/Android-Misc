package com.arch.mvp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Chatikyan on 22.05.2017.
 */

public abstract class BaseV4Fragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends Fragment implements BaseContract.View {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        boolean isPresenterCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            isPresenterCreated = true;
        }
        presenter = viewModel.getPresenter();
        presenter.attachLifecycle(getLifecycle());
        if (isPresenterCreated) {
            presenter.onPresenterCreated();
        }
    }

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        super.onActivityCreated(savedState);
    }

    @CallSuper
    @Override
    public void onViewStateRestored(@Nullable Bundle savedState) {
        super.onViewStateRestored(savedState);
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        presenter.attachView((V) this);
        if (isSupportLazyLoad() && getUserVisibleHint() && !isHidden()) {
            onLazyLoad();
        }
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachLifecycle(getLifecycle());
        presenter = null;
    }

    protected abstract P initPresenter();

    // 默认不支持懒加载, 子类可继承修改此返回值
    protected boolean isSupportLazyLoad() {
        return false;
    }

    // 是否可以执行懒加载
    private void requireLoad() {
        if (getUserVisibleHint() && isSupportLazyLoad() && isResumed() && !isHidden()) {
            onLazyLoad();
        }
    }

    // 执行懒加载
    protected void onLazyLoad() {

    }

    @CallSuper
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        requireLoad();
    }

    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        requireLoad();
    }
}
