package com.artshell.misc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artshell.misc.R;

/**
 * <a href="https://developer.android.google.cn/guide/topics/resources/runtime-changes">Handle configuration changes</a>
 * @author artshell on 2018/6/3
 */
public class ConfigurationChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_change);
    }

}
