package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class ContextViewModel extends AndroidViewModel {

    protected static final ThreadLocal<Gson> threadGson = new ThreadLocal<Gson>() {
        @Override
        protected Gson initialValue() {
            return new Gson();
        }
    };

    public ContextViewModel(@NonNull Application application) {
        super(application);
    }
}
