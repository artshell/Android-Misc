package com.artshell.misc.rv.official_paging_example.repository.inMemory.byPage;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.api.RedditApi.ListingData;
import com.artshell.misc.rv.official_paging_example.api.RedditApi.ListingResponse;
import com.artshell.misc.rv.official_paging_example.api.RedditApi.RedditChildrenResponse;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A data source that uses the before/after keys returned in page requests.
 * <p>
 * See ItemKeyedSubredditDataSource
 */
public class PageKeyedSubredditDataSource extends PageKeyedDataSource<String, RedditPost> {
    private RedditApi webservice;
    private String subredditName;
    private Executor retryExecutor;

    // keep a function reference for the retry event
    private Runnable retry;

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private MutableLiveData<NetworkState> initialLoad  = new MutableLiveData<>();

    public PageKeyedSubredditDataSource(RedditApi webservice, String subredditName, Executor retryExecutor) {
        this.webservice = webservice;
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
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, RedditPost> callback) {
        Call<ListingResponse> request = webservice.getTop(subredditName, params.requestedLoadSize);
        networkState.postValue(NetworkState.loading());
        initialLoad.postValue(NetworkState.loading());

        // triggered by a refresh, we better execute sync
        try {
            Response<ListingResponse> response = request.execute();
            ListingResponse body = response.body();

            if (body != null
                    && body.getData() != null
                    && body.getData().getChildren() != null) {
                ListingData data = body.getData();
                List<RedditChildrenResponse> children = data.getChildren();
                List<RedditPost> items = new ArrayList<>();
                for (RedditChildrenResponse child : children) {
                    items.add(child.getPost());
                }
                callback.onResult(items, data.getBefore(), data.getAfter());
            }
        } catch (IOException ioex) {
            retry = () -> loadInitial(params, callback);
            NetworkState error = NetworkState.error(ioex.getMessage() == null ? "unknown error" : ioex.getMessage());
            networkState.postValue(error);
            initialLoad.postValue(error);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, RedditPost> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, RedditPost> callback) {
        networkState.postValue(NetworkState.loading());
        webservice.getTopAfter(subredditName, params.key, params.requestedLoadSize)
                .enqueue(new Callback<ListingResponse>() {
                    @Override
                    public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                        if (response.isSuccessful()) {
                            ListingResponse body = response.body();
                            if (body != null
                                    && body.getData() != null
                                    && body.getData().getChildren() != null) {
                                ListingData data = body.getData();
                                List<RedditChildrenResponse> children = data.getChildren();
                                List<RedditPost> items = new ArrayList<>();
                                for (RedditChildrenResponse child : children) {
                                    items.add(child.getPost());
                                }
                                callback.onResult(items, data.getAfter());
                            }
                        } else {
                            retry = () -> loadAfter(params, callback);
                            networkState.postValue(NetworkState.error("error code: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<ListingResponse> call, Throwable t) {
                        retry = () -> loadAfter(params, callback);
                        networkState.postValue(NetworkState.error(t.getMessage() == null ? "unknown error" : t.getMessage()));
                    }
                });
    }
}
