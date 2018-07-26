package com.artshell.misc.rv.official_paging_example.repository;

import android.support.annotation.Nullable;

/**
 * @author artshell on 2018/7/25
 */
public class NetworkState {
    public Status mStatus;
    public @Nullable String msg;

    private NetworkState(Status status, @Nullable String msg) {
        mStatus = status;
        this.msg = msg;
    }

    public static NetworkState loaded() {
        return new NetworkState(Status.SUCCESS, null);
    }

    public static NetworkState loading() {
        return new NetworkState(Status.RUNNING, null);
    }

    public static NetworkState error(@Nullable String msg) {
        return new NetworkState(Status.FAILED, msg);
    }

    enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}
