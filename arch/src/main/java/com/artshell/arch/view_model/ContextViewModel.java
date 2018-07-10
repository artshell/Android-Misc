package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class ContextViewModel extends AndroidViewModel {

    public ContextViewModel(@NonNull Application application) {
        super(application);
    }

    protected static ThreadLocal<Gson> singleton() {
        return LocalGson.LOCAL;
    }

    private static class LocalGson {
        private static final ThreadLocal<Gson> LOCAL = new ThreadLocal<Gson>() {
            @Override
            protected Gson initialValue() {
                return new Gson();
            }
        };
    }
}
