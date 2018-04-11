package com.artshell.arch.storage.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.arch.storage.db.dao.CityDao;
import com.artshell.arch.storage.db.entity.City;

@Database(entities = {City.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
