package com.artshell.misc.rv.official_paging_example.repository.inDb;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.artshell.misc.rv.official_paging_example.repository.NetworkState;
import com.artshell.misc.rv.official_paging_example.PagingRequestHelper.Request;
import com.artshell.misc.rv.official_paging_example.PagingRequestHelper.RequestType;
import com.artshell.misc.rv.official_paging_example.PagingRequestHelperExt;
import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.api.RedditApi.ListingResponse;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
public class SubredditBoundaryCallback extends PagedList.BoundaryCallback<RedditPost> {
    private String         subredditName;
    private RedditApi      webservice;
    private ResultCallback mCallback;
    private Executor       ioExecutor;
    private int            pageSize;

    private PagingRequestHelperExt mHelper;
    private LiveData<NetworkState> networkState;

    public SubredditBoundaryCallback(String subredditName,
                                     RedditApi webservice,
                                     ResultCallback callback,
                                     Executor ioExecutor,
                                     int pageSize) {

        this.subredditName = subredditName;
        this.webservice = webservice;
        this.mCallback = callback;
        this.ioExecutor = ioExecutor;
        this.pageSize = pageSize;
        mHelper = new PagingRequestHelperExt(ioExecutor);
        networkState = mHelper.createStatusLiveData();
    }

    public PagingRequestHelperExt getHelper() {
        return mHelper;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @Override
    public void onZeroItemsLoaded() {
        mHelper.runIfNotRunning(
                RequestType.INITIAL,
                callback -> webservice.getTop(subredditName, pageSize)
                        .enqueue(createWebserviceCallback(callback))
        );
    }

    /**
     * User reached to the end of the list.
     */
    @Override
    public void onItemAtEndLoaded(@NonNull RedditPost itemAtEnd) {
        mHelper.runIfNotRunning(
                RequestType.INITIAL,
                callback -> webservice.getTopAfter(subredditName, itemAtEnd.name, pageSize)
                        .enqueue(createWebserviceCallback(callback))
        );
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull RedditPost itemAtFront) {
        // ignored, since we only ever append to what's in the DB
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private void insertItemsIntoDb(Response<ListingResponse> response, Request.Callback it) {
        ioExecutor.execute(() -> {
            mCallback.handleResponse(subredditName, response.body());
            it.recordSuccess();
        });
    }

    private Callback<ListingResponse> createWebserviceCallback(Request.Callback it) {
        return new Callback<ListingResponse>() {
            @Override
            public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                insertItemsIntoDb(response, it);
            }

            @Override
            public void onFailure(Call<ListingResponse> call, Throwable t) {
                it.recordFailure(t);
            }
        };
    }
}
