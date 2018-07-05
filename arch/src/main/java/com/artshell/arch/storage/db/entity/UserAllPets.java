package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class UserAllPets {

    @Embedded
    public User user;

    @Relation(parentColumn = "desc", entityColumn = "userId", entity = Pet.class)
    public List<PetAndId> pets;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = Pet.class, projection = {"name"})
    public List<String> petNames;

    public UserAllPets() {

    }
}
