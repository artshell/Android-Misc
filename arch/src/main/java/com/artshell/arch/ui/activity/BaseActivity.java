package com.artshell.arch.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author artshell on 28/03/2018
 */
abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        setContentView(applyLayoutId());
        // ...
        setProperty();
        loadData();
    }

    protected abstract int applyLayoutId();

    protected abstract void setProperty();

    protected abstract void loadData();
}
