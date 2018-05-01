package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author artshell on 03/04/2018
 */
@Entity(tableName = "app_city", indices = {@Index("country_en"), @Index(value = {"country_id", "city_id"}, unique = true)})
public class City {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    private int id;

    @ColumnInfo(name = "country_cn", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String countryCn;

    @ColumnInfo(name = "country_en", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String countryEn;

    @ColumnInfo(name = "country_id", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String countryId;

    @ColumnInfo(name = "city_cn", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String cityCn;

    @ColumnInfo(name = "city_en", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String cityEn;

    @ColumnInfo(name = "city_id", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.RTRIM)
    private String cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryCn() {
        return countryCn;
    }

    public void setCountryCn(String countryCn) {
        this.countryCn = countryCn;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityCn() {
        return cityCn;
    }

    public void setCityCn(String cityCn) {
        this.cityCn = cityCn;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
