package com.artshell.misc.arch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.artshell.misc.arch.dao.OperateDao;
import com.artshell.misc.arch.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class OperateDatabase extends RoomDatabase {
    abstract OperateDao operate();
}
