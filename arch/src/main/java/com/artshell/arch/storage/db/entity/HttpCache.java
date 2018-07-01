package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author artshell on 2018/6/30
 */
@Entity(tableName = "http_cache", indices = @Index(value = {"cache_key"}, name = "index_cache_key", unique = true))
public class HttpCache {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    private int id;

    @ColumnInfo(name = "cache_key", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String key;

    @ColumnInfo(name = "cache_content", typeAffinity = ColumnInfo.TEXT)
    private String content;

    @ColumnInfo(name = "cache_time", typeAffinity = ColumnInfo.TEXT)
    private String time;

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
