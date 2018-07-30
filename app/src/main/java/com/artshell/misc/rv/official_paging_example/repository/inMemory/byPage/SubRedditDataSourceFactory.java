package com.artshell.misc.rv.official_paging_example.repository.inMemory.byPage;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;

import java.util.concurrent.Executor;

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
public class SubRedditDataSourceFactory extends DataSource.Factory<String, RedditPost> {

    private RedditApi webService;
    private String subredditName;
    private Executor retryExecutor;
    private MutableLiveData<PageKeyedSubredditDataSource> sourceLiveData = new MutableLiveData<>();

    public SubRedditDataSourceFactory(RedditApi webService, String subredditName, Executor retryExecutor) {
        this.webService = webService;
        this.subredditName = subredditName;
        this.retryExecutor = retryExecutor;
    }

    public MutableLiveData<PageKeyedSubredditDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

    @Override
    public DataSource<String, RedditPost> create() {
        PageKeyedSubredditDataSource source = new PageKeyedSubredditDataSource(webService, subredditName, retryExecutor);
        sourceLiveData.postValue(source);
        return source;
    }
}
