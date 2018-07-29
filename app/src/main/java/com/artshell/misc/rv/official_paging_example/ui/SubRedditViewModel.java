package com.artshell.misc.rv.official_paging_example.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.Listing;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;

/**
 * A RecyclerView ViewHolder that displays a single reddit post.
 */
public class SubRedditViewModel extends ViewModel {
    private RedditPostRepository repository;
    private MutableLiveData<String> subredditName = new MutableLiveData<>();
    private LiveData<Listing<RedditPost>> repoResult = Transformations.map(subredditName, name -> repository.postsSubreddit(name, 30));
    public LiveData<PagedList<RedditPost>> posts = Transformations.switchMap(repoResult, Listing::getPagedList);
    public LiveData<NetworkState> networkState = Transformations.switchMap(repoResult, Listing::getNetworkState);
    public LiveData<NetworkState> refreshState = Transformations.switchMap(repoResult, Listing::getRefreshState);

    public SubRedditViewModel(@NonNull RedditPostRepository repository) {
        this.repository = repository;
    }

    public void refresh() {
        Listing<RedditPost> value = repoResult.getValue();
        if (value != null && value.getRefresh() != null) {
            value.getRefresh().invoke();
        }
    }

    public void retry() {
        Listing<RedditPost> value = repoResult.getValue();
        if (value != null && value.getRetry() != null) {
            value.getRefresh().invoke();
        }
    }

    public boolean showSubreddit(@NonNull String subreddit) {
        if (subreddit.equals(subredditName.getValue())) {
            return false;
        }
        subredditName.setValue(subreddit);
        return true;
    }

    @Nullable
    public String currentSubreddit() {
        return subredditName.getValue();
    }
}
