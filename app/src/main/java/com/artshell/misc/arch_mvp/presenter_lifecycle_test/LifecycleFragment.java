package com.artshell.misc.arch_mvp.presenter_lifecycle_test;

import com.arch.mvp.BaseAnnotatedV4Fragment;
import com.arch.mvp.Viewable;
import com.artshell.misc.R;

@Viewable(presenter = FragmentLifecyclePresenter.class, layout = R.layout.fragment_lifecycle)
public class LifecycleFragment extends BaseAnnotatedV4Fragment<LifecycleContract.View, LifecycleContract.Presenter>
        implements LifecycleContract.View {

}
