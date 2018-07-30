package com.artshell.misc.rv.official_paging_example.repository.inMemory.byPage;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.Listing;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;

import java.util.concurrent.Executor;

import static android.arch.lifecycle.Transformations.switchMap;

/**
 * Repository implementation that returns a Listing that loads data directly from network by using
 * the previous / next page keys returned in the query.
 */
public class InMemoryByPageKeyRepository implements RedditPostRepository {

    private RedditApi webService;
    private Executor  networkExecutor;

    public InMemoryByPageKeyRepository(RedditApi webService, Executor networkExecutor) {
        this.webService = webService;
        this.networkExecutor = networkExecutor;
    }

    @Override
    public Listing<RedditPost> postsSubreddit(String subReddit, int pageSize) {
        SubRedditDataSourceFactory sourceFactory = new SubRedditDataSourceFactory(webService, subReddit, networkExecutor);

        // provide custom executor for network requests, otherwise it will default to
        // Arch Components' IO pool which is also used for disk access
        LivePagedListBuilder<String, RedditPost> pageList = new LivePagedListBuilder<>(sourceFactory, pageSize)
                                                            .setFetchExecutor(networkExecutor);

        LiveData<NetworkState> refreshState = switchMap(sourceFactory.getSourceLiveData(), PageKeyedSubredditDataSource::getInitialLoad);

        return new Listing<>(
                pageList.build(),
                switchMap(sourceFactory.getSourceLiveData(), PageKeyedSubredditDataSource::getNetworkState),
                refreshState,
                () -> {
                    PageKeyedSubredditDataSource source = sourceFactory.getSourceLiveData().getValue();
                    if (source != null) {
                        source.retryAllFailed();
                    }
                },
                () -> {
                    PageKeyedSubredditDataSource source = sourceFactory.getSourceLiveData().getValue();
                    if (source != null) {
                        source.invalidate();
                    }
                }
        );
    }
}
