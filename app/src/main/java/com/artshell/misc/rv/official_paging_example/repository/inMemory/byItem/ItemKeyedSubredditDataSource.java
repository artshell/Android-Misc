package com.artshell.misc.rv.official_paging_example.repository.inMemory.byItem;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A data source that uses the "name" field of posts as the key for next/prev pages.
 * <p>
 * Note that this is not the correct consumption of the Reddit API but rather shown here as an
 * alternative implementation which might be more suitable for your backend.
 * see PageKeyedSubredditDataSource for the other sample.
 */
public class ItemKeyedSubredditDataSource extends ItemKeyedDataSource<String, RedditPost> {

    private RedditApi webService;
    private String    subredditName;
    private Executor  retryExecutor;

    // keep a function reference for the retry event
    @Nullable
    private Runnable retry;

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter and we don't support loadBefore
     * in this example.
     * <p>
     * See BoundaryCallback example for a more complete example on syncing multiple network states.
     */
    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();

    public ItemKeyedSubredditDataSource(RedditApi webService, String subredditName, Executor retryExecutor) {
        this.webService = webService;
        this.subredditName = subredditName;
        this.retryExecutor = retryExecutor;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitialLoad() {
        return initialLoad;
    }

    public void retryAllFailed() {
        Runnable prevRetry = retry;
        retry = null;
        if (prevRetry != null) {
            retryExecutor.execute(prevRetry);
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<RedditPost> callback) {
        Call<RedditApi.ListingResponse> request = webService.getTop(subredditName, params.requestedLoadSize);

        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING);
        initialLoad.postValue(NetworkState.LOADING);

        // triggered by a refresh, we better execute sync
        try {
            Response<RedditApi.ListingResponse> response = request.execute();
            List<RedditPost> items = new ArrayList<>();
            if (response.body() != null
                    && response.body().getData() != null
                    && response.body().getData().getChildren() != null) {
                Iterator<RedditApi.RedditChildrenResponse> iter = response.body().getData().getChildren().iterator();
                while (iter.hasNext()) {
                    items.add(iter.next().getPost());
                }
            }
            retry = null;
            networkState.postValue(NetworkState.LOADED);
            initialLoad.postValue(NetworkState.LOADED);
            callback.onResult(items);
        } catch (IOException ioExec) {
            retry = () -> loadInitial(params, callback);
            String message = ioExec.getMessage();
            NetworkState error = NetworkState.error(message == null || message.isEmpty() ? message : "unknown error");
            networkState.postValue(error);
            initialLoad.postValue(error);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<RedditPost> callback) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING);
        // even though we are using async retrofit API here, we could also use sync
        // it is just different to show that the callback can be called async.
        webService.getTopAfter(subredditName, params.key, params.requestedLoadSize)
                .enqueue(new Callback<RedditApi.ListingResponse>() {
                    @Override
                    public void onResponse(Call<RedditApi.ListingResponse> call, Response<RedditApi.ListingResponse> response) {
                        if (response.isSuccessful()) {
                            List<RedditPost> items = new ArrayList<>();
                            if (response.body() != null
                                    && response.body().getData() != null
                                    && response.body().getData().getChildren() != null) {
                                Iterator<RedditApi.RedditChildrenResponse> iter = response.body().getData().getChildren().iterator();
                                while (iter.hasNext()) {
                                    items.add(iter.next().getPost());
                                }
                                // clear retry since last request succeeded
                                retry = null;
                                callback.onResult(items);
                                networkState.postValue(NetworkState.LOADED);
                            }
                        } else {
                            retry = () -> loadAfter(params, callback);
                            networkState.postValue(NetworkState.error("error code: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<RedditApi.ListingResponse> call, Throwable t) {
                        // keep a lambda for future retry
                        retry = () -> loadAfter(params, callback);
                        // publish the error
                        networkState.postValue(NetworkState.error(t.getMessage() == null ? "unknown err" : t.getMessage()));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<RedditPost> callback) {
        // ignored, since we only ever append to our initial load
    }

    /**
     * The name field is a unique identifier for post items.
     * (no it is not the title of the post :) )
     * https://www.reddit.com/dev/api
     */
    @NonNull
    @Override
    public String getKey(@NonNull RedditPost item) {
        return item.name;
    }
}
