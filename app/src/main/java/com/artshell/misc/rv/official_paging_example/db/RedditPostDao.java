package com.artshell.misc.rv.official_paging_example.db;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RedditPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RedditPost> posts);

    @Query("SELECT * FROM posts WHERE subreddit = :subreddit ORDER BY indexInResponse ASC")
    DataSource.Factory<Integer, RedditPost> postsBySubreddit(String subreddit);

    @Query("DELETE FROM posts WHERE subreddit = :subreddit")
    void deleteBySubreddit(String subreddit);

    @Query("SELECT MAX(indexInResponse) + 1 FROM posts WHERE subreddit = :subreddit")
    int getNextIndexInSubreddit(String subreddit);
}
