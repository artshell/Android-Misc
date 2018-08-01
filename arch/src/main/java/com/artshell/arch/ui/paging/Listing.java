package com.artshell.arch.ui.paging;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.artshell.arch.storage.Resource;

public class Listing<T> {
    /* represents the refresh status to show to the user. Separate from nextPageState,
    this value is importantly only when refresh is requested. */
    public LiveData<Resource<Void>> initialState;

    /* represents the next page request status to show to the user */
    private LiveData<Resource<Void>> nextPageState;

    /* the LiveData of paged lists for the UI to observe */
    public LiveData<PagedList<T>> pagedList;

    /* refreshes the whole data and fetches it from scratch. */
    public Callback refresh;

    /* retries any failed requests. */
    public Callback retry;

    public Listing(
            LiveData<Resource<Void>> initialState,
            LiveData<Resource<Void>> nextPageState,
            LiveData<PagedList<T>> pagedList,
            Callback refresh,
            Callback retry) {
        this.initialState = initialState;
        this.nextPageState = nextPageState;
        this.pagedList = pagedList;
        this.refresh = refresh;
        this.retry = retry;
    }

    public interface Callback {
        void invoke();
    }
}
