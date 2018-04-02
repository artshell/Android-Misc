package com.artshell.arch.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.artshell.arch.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author artshell on 31/01/2018
 */

public class AppContext extends Application {
    private static final Object lock = new Object();
    private volatile static AppContext appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (appContext == null) {
            synchronized (lock) {
                if (appContext == null) {
                    appContext = this;
                }
            }
        }

        if (BuildConfig.DEBUG) {
            enabledStrictMode();

            // 内存泄漏检测
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
            } else {
                LeakCanary.install(this);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 开启Strict模式监控main线程中某些不该有的耗时任务,如：网络访问等
     */
    private static void enabledStrictMode() {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build();
        StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(threadPolicy);
        StrictMode.setVmPolicy(vmPolicy);
    }

    public static AppContext getAppContext() {
        return appContext;
    }
}
