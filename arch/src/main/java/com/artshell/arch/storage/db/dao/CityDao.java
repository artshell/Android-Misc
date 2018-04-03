package com.artshell.arch.storage.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artshell.arch.storage.db.entity.CityEntity;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM app_city")
    LiveData<List<CityEntity>> loadCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCities(List<CityEntity> entities);

    @Query("SELECT * FROM app_city WHERE id=:id")
    LiveData<CityEntity> getCity(String id);
}
