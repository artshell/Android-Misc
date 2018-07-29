package com.artshell.misc.rv.official_paging_example.repository;

import android.support.annotation.Nullable;

/**
 * @author artshell on 2018/7/25
 */
public class NetworkState {
    public Status mStatus;
    public @Nullable String msg;

    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, null);
    public static final NetworkState LOADING = new NetworkState(Status.RUNNING, null);

    private NetworkState(Status status, @Nullable String msg) {
        mStatus = status;
        this.msg = msg;
    }

    public static NetworkState error(@Nullable String msg) {
        return new NetworkState(Status.FAILED, msg);
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}
