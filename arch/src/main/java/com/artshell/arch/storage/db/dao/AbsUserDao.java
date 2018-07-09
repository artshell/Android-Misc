package com.artshell.arch.storage.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.artshell.arch.storage.db.AbsUserDatabase;
import com.artshell.arch.storage.db.entity.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author artshell on 2018/7/9
 */
@Dao
public abstract class AbsUserDao {
    private final AbsUserDatabase db;

    public AbsUserDao(AbsUserDatabase db) {
        this.db = db;
    }

    @Transaction
    @Query("SELECT id, name FROM user")
    public abstract Flowable<List<User>> getUsers();
}
