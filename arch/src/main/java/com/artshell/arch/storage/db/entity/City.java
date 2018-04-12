package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author artshell on 03/04/2018
 */
@Entity(tableName = "app_city")
public class City {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("country_cn")
    private String countryCn;

    @SerializedName("country_en")
    private String countryEn;

    @SerializedName("country_id")
    private String countryId;

    @SerializedName("city_cn")
    private String cityCn;

    @SerializedName("city_en")
    private String cityEn;

    @SerializedName("city_id")
    private String cityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
