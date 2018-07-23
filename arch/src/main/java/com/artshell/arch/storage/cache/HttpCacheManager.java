package com.artshell.arch.storage.cache;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.artshell.arch.storage.db.HttpCacheDatabase;
import com.artshell.arch.storage.db.entity.HttpCache;
import com.artshell.arch.storage.server.HttpManager;
import com.google.gson.Gson;
import com.nytimes.android.external.store3.base.impl.room.StoreRoom;
import com.nytimes.android.external.store3.base.room.RoomPersister;

import java.util.Date;

import javax.annotation.Nonnull;

import io.reactivex.Observable;

/**
 * 适合大数据量缓存
 * @see PreferCacheManager
 */
public class HttpCacheManager {

    /**
     * GET请求(无参数)
     * @param target 实体class
     * @param <T>    实体类型
     * @return
     */
    public static <T> StoreRoom<T, Mixture> store(@NonNull Class<T> target) {
        return StoreRoom.from(mixture -> HttpManager.get(String.class, mixture.getPath()).singleOrError(), persistFromRoom(target));
    }

    /**
     * GET请求 + 查询参数
     * @param target 实体class
     * @param <T>    实体类型
     * @return
     */
    public static <T> StoreRoom<T, Mixture2> storeWithParameter(@NonNull Class<T> target) {
        return StoreRoom.from(
                mixture -> HttpManager.get(String.class, mixture.getPath(), mixture.getPairs()).singleOrError(),
                persistFromRoom(target));
    }

    /**
     * POST请求 + (Key/Value), 表单提交
     * @param target 实体class
     * @param <T>    实体类型
     * @return
     */
    public static <T> StoreRoom<T, Mixture2> storeWithCouples(@NonNull Class<T> target) {
        return StoreRoom.from(
                mixture -> HttpManager.post(String.class, mixture.getPath(), mixture.getPairs()).singleOrError(),
                persistFromRoom(target));
    }

    /**
     * 从Database中读取/写入缓存
     * @param clazz
     * @param <T>
     * @param <I>
     * @return
     * @see HttpCacheDatabase
     */
    private static <T, I extends Key> RoomPersister<String, T, I> persistFromRoom(@NonNull Class<T> clazz) {
        return new RoomPersister<String, T, I>() {
            @Nonnull
            @Override
            public Observable<T> read(@Nonnull I mixture) {
                HttpCache cache = HttpCacheDatabase.getInstance().cacheDao().fetch(mixture.getKey());
                if (TextUtils.isEmpty(cache.content)) return Observable.empty();
                if (clazz == String.class) return Observable.just(clazz.cast(cache.content));
                return Observable.just(new Gson().fromJson(cache.content, clazz));
            }

            @Nonnull
            @Override
            public void write(@Nonnull I mixture, @Nonnull String source) {
                HttpCache cache = new HttpCache();
                cache.key = mixture.getKey();
                cache.content = source;
                cache.time = new Date();
                HttpCacheDatabase.getInstance().cacheDao().insertCache(cache);
            }
        };
    }
}
