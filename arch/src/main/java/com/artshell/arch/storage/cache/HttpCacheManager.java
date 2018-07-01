package com.artshell.arch.storage.cache;

import android.arch.persistence.room.Room;

import com.artshell.arch.app.AppContext;
import com.artshell.arch.storage.db.HttpCacheDatabase;
import com.artshell.arch.storage.db.entity.HttpCache;
import com.artshell.arch.storage.server.HttpManager;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.impl.StalePolicy;
import com.nytimes.android.external.store3.base.impl.room.StoreRoom;
import com.nytimes.android.external.store3.base.room.RoomPersister;

import javax.annotation.Nonnull;

import io.reactivex.Observable;

/**
 * @author artshell on 2018/6/30
 */
public class HttpCacheManager {

    // GET 请求(无参数)
    public static StoreRoom<String, Mixture> store() {
        return StoreHolder.MIXTURE;
    }

    // GET 请求 + 查询参数
    public static StoreRoom<String, Mixture2> storeWithParameter() {
        return StoreParameterHolder.MIXTURE;
    }

    // POST 请求 + 字段
    public static StoreRoom<String, Mixture2> storeWithCouples() {
        return StoreCouplesHolder.MIXTURE;
    }

    private static final class StoreHolder {
        private static final StoreRoom<String, Mixture> MIXTURE = createStore(
                mixture -> HttpManager.get(String.class, mixture.getPath()).singleOrError());
    }

    private static final class StoreParameterHolder {
        private static final StoreRoom<String, Mixture2> MIXTURE = createStore(
                mixture -> HttpManager.get(String.class, mixture.getPath(), mixture.getPairs()).singleOrError());
    }

    private static final class StoreCouplesHolder {
        private static final StoreRoom<String, Mixture2> MIXTURE = createStore(
                mixture -> HttpManager.post(String.class, mixture.getPath(), mixture.getPairs()).singleOrError());
    }

    public static <I extends Key> StoreRoom<String, I> createStore(Fetcher<String, I> fetcher) {
        HttpCacheDatabase dbCache = Room.databaseBuilder(AppContext.getAppContext(), HttpCacheDatabase.class, "db_http_cache").build();

        return StoreRoom.from(fetcher, new RoomPersister<String, String, I>() {
            @Nonnull
            @Override
            public Observable<String> read(@Nonnull I key) {
                HttpCache cache = dbCache.cacheDao().fetch(key.getKey());
                return cache == null || cache.getContent() == null || cache.getContent().isEmpty()
                        ? Observable.empty()
                        : Observable.just(cache.getContent());
            }

            @Nonnull
            @Override
            public void write(@Nonnull I key, @Nonnull String s) {
                HttpCache cache = new HttpCache();
                cache.setKey(key.getKey());
                cache.setContent(s);

                dbCache.cacheDao().insertCache(cache);
            }
        }, StalePolicy.NETWORK_BEFORE_STALE);
    }
}
