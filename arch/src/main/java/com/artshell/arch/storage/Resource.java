package com.artshell.arch.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author artshell on 19/03/2018
 */
public class Resource<T> {
    @Status
    @NonNull
    public final String status;
    @Nullable
    public final T data;
    @Nullable
    public final Throwable thr;

    private Resource(@NonNull @Status String status, @Nullable T data, @Nullable Throwable thr) {
        this.status = status;
        this.data = data;
        this.thr = thr;
    }

    /**
     * 加载成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    /**
     * 加载失败
     * @param thr
     * @param <T>
     * @return
     */
    public static <T> Resource<T> error(@NonNull Throwable thr) {
        return new Resource<>(Status.ERROR, null, thr);
    }

    /**
     * 正在加载(例如：可以显示加载dialog)
     * @param <T>
     * @return
     */
    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }

    /**
     * 任务完成(例如：可以关闭dialog)
     * @param <T>
     * @return
     */
    public static <T> Resource<T> complete() {
        return new Resource<>(Status.COMPLETE, null, null);
    }

    /**
     * 任务取消(例如：可以关闭dialog)
     * @param <T>
     * @return
     */
    public static <T> Resource<T> cancel() {
        return new Resource<>(Status.CANCEL, null, null);
    }
}
