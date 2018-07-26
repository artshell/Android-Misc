package com.artshell.misc.rv.official_paging_example;

import android.util.Log;

import com.artshell.misc.BuildConfig;
import com.artshell.misc.rv.official_paging_example.api.RedditApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditApiCreator {
    private static final String BASE_URL = "https://www.reddit.com/";

    public static RedditApi create() {
        HttpLoggingInterceptor.Logger logger = msg -> {
            if (BuildConfig.DEBUG) {
                Log.d("API", msg);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(logger))
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi.class);
    }
}
