package com.artshell.arch.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author artshell on 31/01/2018
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 内存泄漏检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
