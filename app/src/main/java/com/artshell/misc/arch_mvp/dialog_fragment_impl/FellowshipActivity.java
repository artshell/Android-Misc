package com.artshell.misc.arch_mvp.dialog_fragment_impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.arch.mvp.BaseAnnotatedActivity;
import com.arch.mvp.Viewable;
import com.artshell.misc.R;

@Viewable(presenter = FellowshipPresenter.class, layout = R.layout.activity_fellowship)
public class FellowshipActivity extends BaseAnnotatedActivity<FellowshipContract.View, FellowshipContract.Presenter>
        implements FellowshipContract.View, View.OnClickListener {

    private static final String TAG = "FellowshipActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DialogLoadingFragment dialog = new DialogLoadingFragment();
        dialog.show(getSupportFragmentManager(), TAG);

        // 订阅加载结果
        dialog.fellowship().observe(this, items -> Log.i(TAG, "obtain result : " + items));
    }
}
