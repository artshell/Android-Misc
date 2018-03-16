package com.artshell.arch.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author artshell on 31/01/2018
 */

public class AppContext extends Application {
    private static final Object lock = new Object();
    private static AppContext appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (lock) {
            appContext = this;
        }

        // 内存泄漏检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static AppContext getAppContext() {
        return appContext;
    }
}
