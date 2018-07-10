package com.artshell.misc.arch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.misc.arch.dao.CountryDao;
import com.artshell.misc.arch.entity.City;

@Database(entities = {City.class}, version = 1)
public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDao cityDao();
}
