package com.artshell.misc.rv.official_paging_example.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = RedditPost.class, version = 1, exportSchema = false)
public abstract class RedditDb extends RoomDatabase {
    public abstract RedditPostDao posts();

    public static RedditDb create(Context context, boolean useInMemory) {
        Builder<RedditDb> builder = useInMemory
                ? Room.inMemoryDatabaseBuilder(context, RedditDb.class)
                : Room.databaseBuilder(context, RedditDb.class, "reddit.db");
        return builder.fallbackToDestructiveMigration().build();
    }
}
