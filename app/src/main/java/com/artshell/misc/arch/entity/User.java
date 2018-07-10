package com.artshell.misc.arch.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public Integer id;
    public String name;
    public String lastName;
}
