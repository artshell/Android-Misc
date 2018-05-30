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
    private boolean isConsumed;

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

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        findBackState(savedState);
        presenter.attachView((V) this);
        if (isSupportLazyLoad() && !isConsumed && getUserVisibleHint() && !isHidden()) {
            onLazyLoad();
            isConsumed = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_consumed", false);
    }

    private void findBackState(Bundle outState) {
        if (outState == null || outState.isEmpty()) return;
        isConsumed = outState.getBoolean("is_consumed", false);
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
        if (getUserVisibleHint() && isSupportLazyLoad() && !isConsumed && isResumed() && !isHidden()) {
            onLazyLoad();
            isConsumed = true;
        }
    }

    // 执行懒加载
    protected void onLazyLoad() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        requireLoad();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        requireLoad();
    }
}
