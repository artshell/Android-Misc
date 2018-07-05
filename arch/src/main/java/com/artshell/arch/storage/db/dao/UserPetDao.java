package com.artshell.arch.storage.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.artshell.arch.storage.db.entity.UserOfPets;

import java.util.List;

@Dao
public interface UserPetDao {
    @Transaction
    @Query("SELECT id, name FROM user")
    List<UserOfPets> loadPets();
}
