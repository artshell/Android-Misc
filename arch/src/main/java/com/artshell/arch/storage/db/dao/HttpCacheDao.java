package com.artshell.arch.storage.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RxRoom;

import com.artshell.arch.storage.db.entity.HttpCache;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;

/**
 * @author artshell on 2018/6/30
 */
@Dao
public interface HttpCacheDao {

    @Query("SELECT * FROM http_cache")
    List<HttpCache> getCaches();

    @Query("SELECT * FROM http_cache WHERE cache_key = :key")
    HttpCache fetch(String key);

    /**
     * 慎用,当数据中不存在数据时, null值被过滤掉了
     * @see HttpCacheDao_Impl#fetchAsFlowable(String)
     * @see RxRoom#createFlowable(RoomDatabase, String[], Callable)
     */
    @Query("SELECT * FROM http_cache WHERE cache_key = :key")
    Flowable<HttpCache> fetchAsFlowable(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCache(HttpCache cache);
}
