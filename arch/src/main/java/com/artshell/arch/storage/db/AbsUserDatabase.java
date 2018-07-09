package com.artshell.arch.storage.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.arch.storage.db.dao.AbsUserDao;
import com.artshell.arch.storage.db.entity.User;

/**
 * @author artshell on 2018/7/9
 */
@Database(entities = {User.class}, version = 1)
public abstract class AbsUserDatabase extends RoomDatabase {
    public abstract AbsUserDao userDao();
}
