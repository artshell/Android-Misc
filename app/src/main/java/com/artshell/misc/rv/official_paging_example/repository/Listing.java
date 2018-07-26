package com.artshell.misc.rv.official_paging_example.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

public class Listing<T> {
    /* the LiveData of paged lists for the UI to observe */
    private LiveData<PagedList<T>> pagedList;

    /* represents the network request status to show to the user */
    private LiveData<NetworkState> networkState;

    /* represents the refresh status to show to the user. Separate from networkState,
    this value is importantly only when refresh is requested. */
    private LiveData<NetworkState> refreshState;

    /* refreshes the whole data and fetches it from scratch. */
    private Callback refresh;

    /* retries any failed requests. */
    private Callback retry;

    public Listing(LiveData<PagedList<T>> pagedList,
                   LiveData<NetworkState> networkState,
                   LiveData<NetworkState> refreshState,
                   Callback refresh, Callback retry) {
        this.pagedList = pagedList;
        this.networkState = networkState;
        this.refreshState = refreshState;
        this.refresh = refresh;
        this.retry = retry;
    }

    public LiveData<PagedList<T>> getPagedList() {
        return pagedList;
    }

    public void setPagedList(LiveData<PagedList<T>> pagedList) {
        this.pagedList = pagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void setNetworkState(LiveData<NetworkState> networkState) {
        this.networkState = networkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return refreshState;
    }

    public void setRefreshState(LiveData<NetworkState> refreshState) {
        this.refreshState = refreshState;
    }

    public Callback getRefresh() {
        return refresh;
    }

    public void setRefresh(Callback refresh) {
        this.refresh = refresh;
    }

    public Callback getRetry() {
        return retry;
    }

    public void setRetry(Callback retry) {
        this.retry = retry;
    }

    public interface Callback {
        void action();
    }
}
