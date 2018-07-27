package com.artshell.misc.rv.official_paging_example.repository.inMemory.byItem;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.Listing;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;

import java.util.concurrent.Executor;

/**
 * Repository implementation that returns a Listing that loads data directly from the network
 * and uses the Item's name as the key to discover prev/next pages.
 */
public class InMemoryByItemRepository implements RedditPostRepository {

    private RedditApi webService;
    private Executor  networkExecutor;

    public InMemoryByItemRepository(RedditApi webService, Executor networkExecutor) {
        this.webService = webService;
        this.networkExecutor = networkExecutor;
    }

    @MainThread
    @Override
    public Listing<RedditPost> postsSubreddit(String subReddit, int pageSize) {
        SubRedditDataSourceFactory sourceFactory = new SubRedditDataSourceFactory(webService, subReddit, networkExecutor);
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPageSize(pageSize)
                .build();

        // provide custom executor for network requests, otherwise it will default to
        // Arch Components' IO pool which is also used for disk access
        LivePagedListBuilder<String, RedditPost> builder = new LivePagedListBuilder<>(sourceFactory, pagedConfig).setFetchExecutor(networkExecutor);

        LiveData<NetworkState> refreshState = Transformations.switchMap(sourceFactory.getSourceLiveData(), ItemKeyedSubredditDataSource::getInitialLoad);

        return new Listing<>(
                builder.build(),
                Transformations.switchMap(sourceFactory.getSourceLiveData(), ItemKeyedSubredditDataSource::getNetworkState),
                refreshState,
                () -> {
                    ItemKeyedSubredditDataSource source = sourceFactory.getSourceLiveData().getValue();
                    if (source != null) {
                        source.retryAllFailed();
                    }
                },
                () -> {
                    ItemKeyedSubredditDataSource source = sourceFactory.getSourceLiveData().getValue();
                    if (source != null) {
                        source.invalidate();
                    }
                }
        );
    }
}
