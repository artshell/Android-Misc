package com.artshell.misc.arch_mvp.manual_impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.artshell.misc.R;
import com.luseen.arch.BaseActivity;

/**
 * @author artshell on 2018/5/1
 */
public class ProgressActivity extends BaseActivity<ProgressContract.View, ProgressContract.Presenter>
        implements ProgressContract.View, View.OnClickListener {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_manual_progress);

        findViewById(R.id.button).setOnClickListener(this);

        mProgressDialog = createDialog(this);
    }

    private ProgressDialog createDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("loading");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.setProgress(0);
        return dialog;
    }

    @Override
    protected ProgressContract.Presenter initPresenter() {
        return new ProgressPresenter();
    }

    @Override
    public void onClick(View v) {
        presenter.requestAfterDelay(15_000L);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.hide();
    }

    @Override
    public void updateProgress(int percent) {
        mProgressDialog.setProgress(percent);
    }
}
