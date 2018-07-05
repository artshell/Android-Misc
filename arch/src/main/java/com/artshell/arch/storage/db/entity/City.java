package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author artshell on 03/04/2018
 */
@Entity(tableName = "city_snippet", indices = {@Index("country_en"), @Index(value = {"country_id", "city_id"}, unique = true)})
public class City {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    public int id;

    @ColumnInfo(name = "country_cn", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String countryCn;

    @ColumnInfo(name = "country_en", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String countryEn;

    @ColumnInfo(name = "country_id", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String countryId;

    @ColumnInfo(name = "city_cn", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String cityCn;

    @ColumnInfo(name = "city_en", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String cityEn;

    @ColumnInfo(name = "city_id", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    public String cityId;
}
