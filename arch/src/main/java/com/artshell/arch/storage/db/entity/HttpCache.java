package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.db.DateConverters;

import java.util.Date;

/**
 * @author artshell on 2018/6/30
 */
@Entity(tableName = "http_cache", indices = @Index(value = {"cache_key"}, name = "index_cache_key", unique = true))
@TypeConverters(value = DateConverters.class)
public class HttpCache {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    public Integer id;

    @NonNull
    @ColumnInfo(name = "cache_key", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String key;

    @ColumnInfo(name = "cache_content", typeAffinity = ColumnInfo.TEXT)
    public String content = "";

    @ColumnInfo(name = "cache_time")
    public Date time;
}
