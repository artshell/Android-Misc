package com.artshell.mvp.storage.server;

import com.artshell.requestor.Protocol;
import com.artshell.requestor.RxRequestor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by artshell on 2018/3/16.
 */

public class HttpManager {
    private static final RxRequestor requestor;

    static {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                /* more settings */
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.github.com") /* Your server address */
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(true)
                .client(client)
                /* more settings */
                .build();

        requestor = new RxRequestor.Builder()
                .setRetrofit(retrofit)
                .supportBy(Protocol.JSON)
                .build();
    }

    public static <T> Flowable<T> get(Class<T> target, String url) {
        return requestor.get(target, url);
    }

    public static <T> Flowable<T> get(Class<T> target, String url, Map<String, String> queryPairs) {
        return requestor.getWithParameters(target, url, queryPairs);
    }

    public static <T> Flowable<T> post(Class<T> target, String url, Map<String, String> postPairs) {
        return requestor.postFields(target, url, postPairs);
    }
}
