package com.artshell.misc.arch.entity;

import android.arch.persistence.room.Relation;
import android.arch.persistence.room.RoomWarnings;

import java.util.List;

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
public class UserOfPets {
    public Integer id;
    public String name;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<Pet> pets;

    public UserOfPets() {
    }
}
