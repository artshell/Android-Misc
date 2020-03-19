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
        private ListingData data;

        public ListingResponse(ListingData data) {
            this.data = data;
        }

        public ListingData getData() {
            return data;
        }

        public void setData(ListingData data) {
            this.data = data;
        }
    }

    class ListingData {
        private List<RedditChildrenResponse> children;

        @Nullable
        private String before;

        @Nullable
        private String after;

        public ListingData(List<RedditChildrenResponse> children, @Nullable String before, @Nullable String after) {
            this.children = children;
            this.before = before;
            this.after = after;
        }

        public List<RedditChildrenResponse> getChildren() {
            return children;
        }

        public void setChildren(List<RedditChildrenResponse> children) {
            this.children = children;
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
        private RedditPost data;

        public RedditPost getPost() {
            return data;
        }

        public void setPost(RedditPost data) {
            this.data = data;
        }
    }
}
