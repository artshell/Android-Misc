package com.artshell.misc.arch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.misc.arch.dao.RawDao;
import com.artshell.misc.arch.dao.UserPetDao;
import com.artshell.misc.arch.dao.UserPetDao2;
import com.artshell.misc.arch.entity.Pet;
import com.artshell.misc.arch.entity.User;

@Database(entities = {Pet.class, User.class}, version = 1)
public abstract class PetDatabase extends RoomDatabase {
    public abstract UserPetDao petDao();
    public abstract UserPetDao2 petDao2();
    public abstract RawDao rawDao();
}
