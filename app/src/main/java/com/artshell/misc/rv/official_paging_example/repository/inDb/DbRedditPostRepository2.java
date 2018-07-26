package com.artshell.misc.rv.official_paging_example.repository.inDb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.api.RedditApi.RedditChildrenResponse;
import com.artshell.misc.rv.official_paging_example.db.RedditDb;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.Listing;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DbRedditPostRepository2 implements RedditPostRepository {
    private static final int DEFAULT_NETWORK_PAGE_SIZE = 10;

    private RedditDb  db;
    private RedditApi webService;
    private Executor  ioExecutor;
    private Integer pageSize = DEFAULT_NETWORK_PAGE_SIZE;

    public DbRedditPostRepository2(RedditDb db, RedditApi webService, Executor ioExecutor, Integer pageSize) {
        this.db = db;
        this.webService = webService;
        this.ioExecutor = ioExecutor;
        this.pageSize = pageSize;
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private void insertResultIntoDb(String subredditName, @Nullable RedditApi.ListingResponse response) {
        if (response == null) return;
        List<RedditChildrenResponse> children = response.getData().getChildren();
        if (children == null || children.isEmpty()) return;
        db.runInTransaction(() -> {
            int start = db.posts().getNextIndexInSubreddit(subredditName);
            List<RedditPost> items = new ArrayList<>();
            Iterator<RedditChildrenResponse> iter = children.iterator();
            int index = 0;
            while (iter.hasNext()) {
                int i = index++;
                RedditChildrenResponse child = iter.next();
                child.getPost().indexInResponse = start + i;
                items.add(child.getPost());
            }
            db.posts().insert(items);
        });
    }

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private LiveData<NetworkState> refresh(String subredditName) {
        MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        networkState.setValue(NetworkState.loading());
        webService.getTop(subredditName, pageSize)
                .enqueue(new Callback<RedditApi.ListingResponse>() {
                    @Override
                    public void onResponse(Call<RedditApi.ListingResponse> call, Response<RedditApi.ListingResponse> response) {
                        ioExecutor.execute(() -> {
                            db.runInTransaction(() -> {
                                db.posts().deleteBySubreddit(subredditName);
                                insertResultIntoDb(subredditName, response.body());
                            });

                            // since we are in bg thread now, post the result.
                            networkState.postValue(NetworkState.loaded());
                        });
                    }

                    @Override
                    public void onFailure(Call<RedditApi.ListingResponse> call, Throwable t) {
                        networkState.setValue(NetworkState.error(t.getMessage()));
                    }
                });

        return networkState;
    }

    /**
     * Returns a Listing for the given subreddit.
     */
    @Override
    public Listing<RedditPost> postsSubreddit(String subReddit, int pageSize) {
        // create a boundary callback which will observe when the user reaches to the edges of
        // the list and update the database with extra data.
        SubredditBoundaryCallback boundaryCallback = new SubredditBoundaryCallback(
                subReddit,
                webService,
                this::insertResultIntoDb,
                ioExecutor,
                pageSize);

        // create a data source factory from Room
        DataSource.Factory<Integer, RedditPost> factory = db.posts().postsBySubreddit(subReddit);
        LivePagedListBuilder<Integer, RedditPost> builder = new LivePagedListBuilder<>(factory, pageSize)
                .setBoundaryCallback(boundaryCallback);

        // we are using a mutable live data to trigger refresh requests which eventually calls
        // refresh method and gets a new live data. Each refresh request by the user becomes a newly
        // dispatched data in refreshTrigger
        MutableLiveData<Void> refreshTrigger = new MutableLiveData<>();
        LiveData<NetworkState> refreshState = Transformations.switchMap(refreshTrigger, v -> refresh(subReddit));
        return new Listing<>(
                builder.build(),
                boundaryCallback.getNetworkState(),
                refreshState,
                () -> boundaryCallback.getHelper().retryAllFailed(),
                () -> refreshTrigger.postValue(null)
        );
    }
}
