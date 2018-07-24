package com.artshell.misc.rv.helper;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.artshell.misc.rv.helper.PagingRequestHelper.RequestType;
import com.artshell.misc.rv.helper.RedditApi.ListingResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagingBoundaryCallback extends PagedList.BoundaryCallback<String> {
    Executor mExecutor = Executors.newSingleThreadExecutor();
    PagingRequestHelper mHelper = new PagingRequestHelper(mExecutor);
    RedditApi api = RedditApiCreator.create();

    @Override
    public void onItemAtFrontLoaded(@NonNull String itemAtFront) {
        mHelper.runIfNotRunning(
                RequestType.BEFORE,
                callback -> api.getTopBefore("subreddit", itemAtFront, 10).enqueue(new Callback<ListingResponse>() {
                    @Override
                    public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                        callback.recordSuccess();
                    }

                    @Override
                    public void onFailure(Call<ListingResponse> call, Throwable t) {
                        callback.recordFailure(t);
                    }
                })
        );
    }

    @Override
    public void onItemAtEndLoaded(@NonNull String itemAtEnd) {
        mHelper.runIfNotRunning(
                RequestType.AFTER,
                callback -> api.getTopBefore("subreddit", itemAtEnd, 10).enqueue(new Callback<ListingResponse>() {
                    @Override
                    public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                        callback.recordSuccess();
                    }

                    @Override
                    public void onFailure(Call<ListingResponse> call, Throwable t) {
                        callback.recordFailure(t);
                    }
                })
        );
    }
}
