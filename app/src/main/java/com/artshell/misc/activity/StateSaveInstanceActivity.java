package com.artshell.misc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artshell.misc.R;

/**
 * @see StateRetainObjectViewModelActivity
 */
public class StateSaveInstanceActivity extends AppCompatActivity {
    private static final String TAG = "SaveInstanceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_save_instance);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
