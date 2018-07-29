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

    public RedditPost() { }

    public RedditPost(RedditPost post) {
        this(post.name,
                post.title,
                post.score,
                post.author,
                post.subreddit,
                post.num_comments,
                post.created,
                post.thumbnail,
                post.url,
                post.indexInResponse);
    }

    public RedditPost(@NonNull String name,
                      String title,
                      int score,
                      String author,
                      String subreddit,
                      int num_comments,
                      long created,
                      String thumbnail,
                      String url,
                      int indexInResponse) {
        this.name = name;
        this.title = title;
        this.score = score;
        this.author = author;
        this.subreddit = subreddit;
        this.num_comments = num_comments;
        this.created = created;
        this.thumbnail = thumbnail;
        this.url = url;
        this.indexInResponse = indexInResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedditPost that = (RedditPost) o;

        if (score != that.score) return false;
        if (num_comments != that.num_comments) return false;
        if (created != that.created) return false;
        if (indexInResponse != that.indexInResponse) return false;
        if (!name.equals(that.name)) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (subreddit != null ? !subreddit.equals(that.subreddit) : that.subreddit != null) return false;
        if (thumbnail != null ? !thumbnail.equals(that.thumbnail) : that.thumbnail != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (subreddit != null ? subreddit.hashCode() : 0);
        result = 31 * result + num_comments;
        result = 31 * result + (int) (created ^ (created >>> 32));
        result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + indexInResponse;
        return result;
    }
}
