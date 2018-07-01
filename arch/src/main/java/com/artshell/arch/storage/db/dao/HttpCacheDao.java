package com.artshell.arch.storage.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artshell.arch.storage.db.entity.HttpCache;

import java.util.List;

/**
 * @author artshell on 2018/6/30
 */
@Dao
public interface HttpCacheDao {

    @Query("SELECT * FROM http_cache")
    List<HttpCache> getCaches();

    @Query("SELECT * FROM http_cache WHERE cache_key = :key")
    HttpCache fetch(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCache(HttpCache cache);
}
