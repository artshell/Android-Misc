package com.artshell.misc.rv.helper;

import android.support.annotation.Nullable;

/**
 * @author artshell on 2018/7/25
 */
public class NetworkState2 {
    public Status mStatus;
    public @Nullable String msg;

    public static NetworkState2 LOADED = new NetworkState2(Status.SUCCESS, null);
    public static NetworkState2 LOADING = new NetworkState2(Status.RUNNING, null);

    public NetworkState2(Status status, @Nullable String msg) {
        mStatus = status;
        this.msg = msg;
    }

    public static NetworkState2 error(@Nullable String msg) {
        return new NetworkState2(Status.FAILED, msg);
    }

    enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}
