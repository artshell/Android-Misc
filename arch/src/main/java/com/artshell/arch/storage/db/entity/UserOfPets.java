package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.Relation;

import java.util.List;

public class UserOfPets {
    public Integer id;
    public String name;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<Pet> pets;

    public UserOfPets() {
    }
}
