package com.artshell.misc.rv.official_paging_example.repository.inMemory.byItem;

import android.arch.paging.DataSource;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;

import java.util.concurrent.Executor;

/**
 * @author artshell on 2018/7/26
 */
public class SubRedditDataSourceFactory extends DataSource.Factory<String, RedditPost> {
    private RedditApi webService;
    private String subredditName;
    private Executor retryExecutor;



    @Override
    public DataSource<String, RedditPost> create() {
        return null;
    }
}
