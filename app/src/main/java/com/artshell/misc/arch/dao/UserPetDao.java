package com.artshell.misc.arch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Transaction;

import com.artshell.misc.arch.entity.UserOfPets;

import java.util.List;

@Dao
public interface UserPetDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT id, name, lastName FROM user")
    List<UserOfPets> loadPets();
}
