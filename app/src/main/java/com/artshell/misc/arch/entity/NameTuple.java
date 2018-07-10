package com.artshell.misc.arch.entity;

import android.arch.persistence.room.ColumnInfo;

// 只查询出 {@link User}的name,lastName字段
public class NameTuple {
    public String name;
    @ColumnInfo(name = "lastName")
    public String last_name;
}
