package com.artshell.misc.arch.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.RoomWarnings;

import java.util.List;

public class UserAllPets {
    @Embedded
    public User user;

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Relation(parentColumn = "id", entityColumn = "userId", entity = Pet.class)
    public List<PetAndId> pets;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = Pet.class, projection = {"name"})
    public List<String> petNames;

    public UserAllPets() {

    }
}
