package com.artshell.misc.rv.paging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.artshell.misc.arch.AbsUserDatabase;
import com.artshell.misc.arch.entity.User;

/**
 * @author artshell on 2018/7/21
 */
public class UserViewModel extends AndroidViewModel {

    private final LiveData<PagedList<User>> userData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        AbsUserDatabase db = Room.databaseBuilder(application, AbsUserDatabase.class, "abs_user").build();
        userData = new LivePagedListBuilder<>(db.userDao().getUsersByLastName(), 20 /* page size */).build();
    }

    public LiveData<PagedList<User>> getUserDatas() {
        return userData;
    }
}
