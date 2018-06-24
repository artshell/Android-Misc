package com.artshell.misc.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.artshell.misc.R;

/**
 * Handling the configuration change yourself
 * <a href="https://developer.android.google.cn/guide/topics/resources/runtime-changes#HandlingTheChange">Handle configuration changes</a>
 * <a href="https://www.jianshu.com/p/dbc7e81aead2">Android 横竖屏切换</a>
 * <a href="https://developer.android.google.cn/guide/topics/manifest/activity-element#config">android:configChanges</a>
 * <a href="https://developer.android.google.cn/guide/topics/manifest/activity-element#screen">android:screenOrientation</a>
 * <a href="https://github.com/xxv/android-lifecycle">Complete Android Fragment & Activity Lifecycle</a>
 * @see ReLauncherActivity
 * @author artshell on 2018/6/3
 */
public class NoReLauncherActivity extends AppCompatActivity {
    private static final String TAG = "NoReLauncherActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_re_launcher_port);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "onConfigurationChanged: ORIENTATION_PORTRAIT");
            // 修改布局文件
            setContentView(R.layout.activity_no_re_launcher_port);
            // findViewById
            // do other something
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "onConfigurationChanged: ORIENTATION_LANDSCAPE");
            // 修改布局文件
            setContentView(R.layout.activity_no_re_launcher_land);
            // findViewById
            // do other something
        }
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