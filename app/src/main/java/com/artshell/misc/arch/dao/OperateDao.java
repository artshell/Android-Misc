package com.artshell.misc.arch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.artshell.misc.arch.entity.NameTuple;
import com.artshell.misc.arch.entity.User;

import java.util.List;

@Dao
public interface OperateDao {

    /* =============== Insert ============== */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArray(User... users);

    @Insert
    void insertArray(User user, List<User> list);

    @Insert
    long insert(User user);

    @Insert
    long[] insertList(User[] users);

    @Insert
    List<Long> insertList(List<User> users);

    /* =============== Update ============== */
    @Update
    void update(User user);

    @Update
    int update(User... users);

    /* =============== Delete ============== */
    @Delete
    int delete(User... users);


    /* =============== Query ============== */
    /**
     * Use this POJO in your query method
     * @see NameTuple
     * @return
     */
    @Query("SELECT name, lastName FROM user")
    List<NameTuple> loadTupe();

    @Query("SELECT name, lastName FROM user WHERE id IN (:regions)")
    List<NameTuple> loadTupeRegions(List<Integer> regions);
}
