package com.artshell.arch.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author artshell on 19/03/2018
 */

public class Result<T> {
    @NonNull public final String status;
    @Nullable public final T data;
    @Nullable public final Throwable thr;

    private Result(@NonNull String status, T data, Throwable thr) {
        this.status = status;
        this.data = data;
        this.thr = thr;
    }

    // 加载成功
    public static <T> Result<T> success(@NonNull T data) {
        return new Result<>(Status.SUCCESS, data, null);
    }

    // 加载失败
    public static <T> Result<T> error(@NonNull Throwable thr) {
        return new Result<>(Status.ERROR, null, thr);
    }

    // 正在加载(例如：显示加载dialog)
    public static <T> Result<T> loading() {
        return new Result<>(Status.LOADING, null, null);
    }

    // 完成(例如：关闭dialog)
    public static <T> Result<T> complete() {
        return new Result<>(Status.COMPLETE, null, null);
    }

    // 任务取消(例如：关闭dialog)
    public static <T> Result<T> cancel() {
        return new Result<>(Status.CANCEL, null, null);
    }
}
