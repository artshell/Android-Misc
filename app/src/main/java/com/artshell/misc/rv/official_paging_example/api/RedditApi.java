package com.artshell.misc.rv.official_paging_example.api;

import android.support.annotation.Nullable;

import com.artshell.misc.rv.official_paging_example.db.RedditPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API communication setup
 */
public interface RedditApi {

    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTop(@Path("subreddit") String subreddit, @Query("limit") int limit);

    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTopBefore(@Path("subreddit") String subreddit, @Query("before") String before, @Query("limit") int limit);

    @GET("/r/{subreddit}/hot.json")
    Call<ListingResponse> getTopAfter(@Path("subreddit") String subreddit, @Query("after") String after, @Query("limit") int limit);

    class ListingResponse {
        private ListingData mData;

        public ListingResponse(ListingData data) {
            mData = data;
        }

        public ListingData getData() {
            return mData;
        }

        public void setData(ListingData data) {
            mData = data;
        }
    }

    class ListingData {
        private List<RedditChildrenResponse> mChildren;

        @Nullable
        private String before;

        @Nullable
        private String after;

        public ListingData(List<RedditChildrenResponse> children, @Nullable String before, @Nullable String after) {
            this.mChildren = children;
            this.before = before;
            this.after = after;
        }

        public List<RedditChildrenResponse> getChildren() {
            return mChildren;
        }

        public void setChildren(List<RedditChildrenResponse> children) {
            mChildren = children;
        }

        @Nullable
        public String getBefore() {
            return before;
        }

        public void setBefore(@Nullable String before) {
            this.before = before;
        }

        @Nullable
        public String getAfter() {
            return after;
        }

        public void setAfter(@Nullable String after) {
            this.after = after;
        }
    }

    class RedditChildrenResponse {
        private RedditPost mPost;

        public RedditPost getPost() {
            return mPost;
        }

        public void setPost(RedditPost post) {
            mPost = post;
        }
    }
}
