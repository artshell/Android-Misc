package com.artshell.arch.storage.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.arch.storage.db.dao.HttpCacheDao;
import com.artshell.arch.storage.db.entity.HttpCache;

/**
 * @author artshell on 2018/6/30
 */
@Database(entities = {HttpCache.class}, version = 1)
public abstract class HttpCacheDatabase extends RoomDatabase {
    public abstract HttpCacheDao cacheDao();
}
