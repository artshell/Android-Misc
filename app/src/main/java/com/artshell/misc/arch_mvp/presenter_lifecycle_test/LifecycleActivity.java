package com.artshell.misc.arch_mvp.presenter_lifecycle_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.arch.mvp.BaseAnnotatedActivity;
import com.arch.mvp.Viewable;
import com.artshell.misc.R;

@Viewable(presenter = ActivityLifecyclePresenter.class, layout = R.layout.activity_lifecycle)
public class LifecycleActivity extends BaseAnnotatedActivity<LifecycleContract.View, LifecycleContract.Presenter>
        implements LifecycleContract.View {

    private static final String TAG = "LifecycleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        findViewById(R.id.btn_test).setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout_container, new LifecycleFragment(), TAG).commit();
    }
}
