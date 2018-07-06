package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public Integer id;
    public String name;
    public String lastName;
}
