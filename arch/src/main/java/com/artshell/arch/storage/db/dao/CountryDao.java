package com.artshell.arch.storage.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artshell.arch.storage.db.entity.City;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM app_city")
    LiveData<List<City>> getCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCities(List<City> entities);

    @Query("SELECT * FROM app_city WHERE id=:id")
    LiveData<City> getCity(String id);
}
