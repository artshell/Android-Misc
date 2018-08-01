package com.artshell.arch.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author artshell on 2018/4/6
 */
public class TaskExecutors {

    public static Executor diskIO() {
        return DiskExecutor.SINGLE_EXECUTOR;
    }

    public static Executor networkIO() {
        return NetworkExecutor.NETWORK_EXECUTOR;
    }

    public static Executor mainThread() {
        return MainExecutor.MAIN_EXECUTOR;
    }

    private static class DiskExecutor {
        private static final Executor SINGLE_EXECUTOR = Executors.newSingleThreadExecutor();
    }

    private static class NetworkExecutor {
        private static final Executor NETWORK_EXECUTOR = new ThreadPoolExecutor(
                1, 2, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(6));
    }

    private static class MainExecutor {
        private static final Executor MAIN_EXECUTOR = new Executor() {
            private Handler mainHandler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(@NonNull Runnable command) {
                mainHandler.post(command);
            }
        };
    }
}
