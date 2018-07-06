package com.artshell.arch.storage.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.arch.storage.db.dao.RawDao;
import com.artshell.arch.storage.db.dao.UserPetDao;
import com.artshell.arch.storage.db.dao.UserPetDao2;
import com.artshell.arch.storage.db.entity.Pet;
import com.artshell.arch.storage.db.entity.User;

@Database(entities = {Pet.class, User.class}, version = 1)
public abstract class PetDatabase extends RoomDatabase {
    public abstract UserPetDao petDao();
    public abstract UserPetDao2 petDao2();
    public abstract RawDao rawDao();
}
