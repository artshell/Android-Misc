package com.artshell.arch.storage.cache;

import android.support.annotation.NonNull;

import com.artshell.arch.storage.prefer.PreferManager;
import com.artshell.arch.storage.server.HttpManager;
import com.google.gson.Gson;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;

import javax.annotation.Nonnull;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * 适合小数据量缓存
 * @see HttpCacheManager
 * @author artshell on 17/03/2018
 */
public class PreferCacheManager {

    /**
     * GET请求(无参数)
     * @return
     * @see Mixture
     */
    public static <T> Store<T, Mixture> store(@NonNull Class<T> target) {
        return createStore(mixture -> HttpManager.get(String.class, mixture.getPath()).singleOrError(), target);
    }

    /**
     * GET请求 + 查询参数
     * @return
     * @see Mixture2
     */
    public static <T> Store<T, Mixture2> storeWithParameter(@NonNull Class<T> target) {
        return createStore(mixture -> HttpManager.get(String.class, mixture.getPath(), mixture.getPairs()).singleOrError(), target);
    }

    /**
     * POST请求 + (Key/Value), 表单提交
     * @return
     * @see Mixture2
     */
    public static <T> Store<T, Mixture2> storeWithCouples(@NonNull Class<T> target) {
        return createStore(mixture -> HttpManager.post(String.class, mixture.getPath(), mixture.getPairs()).singleOrError(), target);
    }

    /**
     * 从SharedReference中读取/写入缓存
     * @param fetcher
     * @param clazz
     * @param <I>
     * @param <T>
     * @return
     */
    private static <I extends Key, T> Store<T, I> createStore(@NonNull Fetcher<String, I> fetcher, @NonNull final Class<T> clazz) {
        return StoreBuilder.<I, String, T>parsedWithKey()
                .fetcher(fetcher)
                .persister(new Persister<String, I>() {
                    @Nonnull
                    @Override
                    public Maybe<String> read(@Nonnull I mixture) {
                        String cache = PreferManager.apiData().get(mixture.getKey(), String.class, "");
                        return cache.length() == 0 ? Maybe.empty() : Maybe.just(cache);
                    }

                    @Nonnull
                    @Override
                    public Single<Boolean> write(@Nonnull I mixture, @Nonnull String source) {
                        PreferManager.apiData().put(mixture.getKey(), source);
                        return Single.just(true);
                    }
                })
                .parser(source -> clazz == String.class ? clazz.cast(source) : new Gson().fromJson(source, clazz))
                .networkBeforeStale()
                .open();
    }
}
