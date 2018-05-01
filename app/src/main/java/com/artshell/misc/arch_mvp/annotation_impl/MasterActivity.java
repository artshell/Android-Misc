package com.artshell.misc.arch_mvp.annotation_impl;

import com.artshell.misc.R;
import com.luseen.arch.BaseAnnotatedActivity;
import com.luseen.arch.Viewable;

/**
 * @author artshell on 2018/5/1
 */
@Viewable(presenter = MasterPresenter.class, layout = R.layout.activity_annotation_master)
public class MasterActivity extends BaseAnnotatedActivity<MasterContract.View, MasterContract.Presenter> implements MasterContract.View {


    @Override
    public void openSubFragment() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
