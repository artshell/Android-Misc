package com.artshell.misc.rv.official_paging_example.repository;

import com.artshell.misc.rv.official_paging_example.db.RedditPost;

/**
 * Common interface shared by the different repository implementations.
 * Note: this only exists for sample purposes - typically an app would implement a repo once, either
 * network+db, or network-only
 */
public interface RedditPostRepository {

    Listing<RedditPost> postsSubreddit(String subReddit, int pageSize);

    enum Type {
        IN_MEMORY_BY_ITEM,
        IN_MEMORY_BY_PAGE,
        DB
    }
}
