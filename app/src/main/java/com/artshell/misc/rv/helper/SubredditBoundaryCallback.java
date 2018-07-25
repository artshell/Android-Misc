package com.artshell.misc.rv.helper;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubredditBoundaryCallback extends PagedList.BoundaryCallback<RedditPost> {

    Executor mExecutor = Executors.newSingleThreadExecutor();
    PagingRequestHelper mHelper = new PagingRequestHelper(mExecutor);
    RedditApi webService = RedditApiCreator.create();

    @Override
    public void onZeroItemsLoaded() {

    }

    @Override
    public void onItemAtEndLoaded(@NonNull RedditPost itemAtEnd) {

    }

    @Override
    public void onItemAtFrontLoaded(@NonNull RedditPost itemAtFront) {

    }
}
