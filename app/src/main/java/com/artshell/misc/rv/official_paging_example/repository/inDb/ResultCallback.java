package com.artshell.misc.rv.official_paging_example.repository.inDb;

import com.artshell.misc.rv.official_paging_example.api.RedditApi.ListingResponse;

public interface ResultCallback {
    void handleResponse(String subredditName, ListingResponse response);
}
