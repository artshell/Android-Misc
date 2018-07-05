package com.artshell.arch.storage.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.artshell.arch.storage.db.entity.UserAllPets;

import java.util.List;

@Dao
public interface UserPetDao2 {
    @Transaction
    @Query("SELECT * FROM user")
    List<UserAllPets> loadPets();
}
