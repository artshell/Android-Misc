package com.artshell.misc.rv.official_paging_example.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "posts", indices = @Index(value = {"subreddit"}))
public class RedditPost {
    @PrimaryKey
    @NonNull
    public String name;
    public String title;
    public int    score;
    public String author;

    @ColumnInfo(collate = ColumnInfo.NOCASE)
    public String subreddit;

    @SerializedName("num_comments")
    public int num_comments;

    @SerializedName("created_utc")
    public long created;

    @Nullable
    public String thumbnail;

    @Nullable
    public String url;

    // to be consistent w/ changing backend order, we need to keep a data like this
    public int indexInResponse = -1;
}
