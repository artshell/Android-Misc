package com.artshell.arch.storage.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.RawQuery;

import com.artshell.arch.storage.db.entity.User;
import com.artshell.arch.storage.db.entity.UserEmbedded;
import com.artshell.arch.storage.db.entity.UserOfPets;
import com.artshell.arch.storage.db.entity.UserPoJo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RawDao {
    @RawQuery
    User getUser(SupportSQLiteQuery query);

    @RawQuery(observedEntities = User.class)
    LiveData<List<User>> getUsers(SupportSQLiteQuery query);

    @RawQuery(observedEntities = User.class)
    Flowable<List<User>> fetchUsers(SupportSQLiteQuery query);

    @RawQuery
    UserPoJo getUserPojo(SupportSQLiteQuery query);

    @RawQuery
    UserEmbedded getUserEmbedded(SupportSQLiteQuery query);

    @RawQuery
    UserOfPets getUserRelation(SupportSQLiteQuery query);
}
