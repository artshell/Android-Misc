package com.artshell.arch.ui.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * @author artshell on 28/03/2018
 */
public abstract class DataBaseActivity extends BaseActivity {
    protected Map<String, String> mPairs = new ArrayMap<>();

    protected <VM extends ViewModel> VM createViewModel(@NonNull Class<VM> target) {
        return ViewModelProviders.of(this).get(target);
    }

    protected <VM extends ViewModel> VM createViewModel(@NonNull String key, @NonNull Class<VM> target) {
        return ViewModelProviders.of(this).get(key, target);
    }

    protected void dispatchError(Throwable thr) {

    }
}
