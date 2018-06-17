package com.artshell.misc.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.artshell.misc.R;

/**
 * Retaining an object during a configuration change
 * <a href="https://developer.android.google.cn/guide/topics/resources/runtime-changes#HandlingTheChange">Handle configuration changes</a>
 * @see StateSaveInstanceActivity
 * @author artshell on 2018/6/3
 */
public class StateRetainObjectViewModelActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ConfigurationViewModel";

    StateRetainObjectViewModel retainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_retain_object_view_model);

        ProgressBar bar = findViewById(R.id.progress);
        Button start = findViewById(R.id.start);
        start.setOnClickListener(this);

        retainViewModel = ViewModelProviders.of(this).get(StateRetainObjectViewModel.class);
        retainViewModel.getParentProgress().observe(this, bar::setProgress);
    }

    @Override
    public void onClick(View v) {
        retainViewModel.startSimulatorTask();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.i(TAG, "onContentChanged: ");
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i(TAG, "onPostCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume: ");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu: ");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu: ");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.i(TAG, "onUserInteraction: ");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.i(TAG, "onUserLeaveHint: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
