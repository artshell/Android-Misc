package com.artshell.misc.arch.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Pet {

    @PrimaryKey
    @NonNull
    public Integer id;

    public Integer userId;

    public String name;


}
