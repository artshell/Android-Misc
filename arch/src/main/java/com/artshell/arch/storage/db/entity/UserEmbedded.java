package com.artshell.arch.storage.db.entity;

import android.arch.persistence.room.Embedded;

public class UserEmbedded {

    @Embedded
    public User user;

    /**
     * 多个 {@link Embedded} 字段对象内不能出现相同字段
     */
//    @Embedded
//    public Pet pet;
}
