package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class BaseContextViewModel extends AndroidViewModel {

    protected static final ThreadLocal<Gson> LOCAL = new ThreadLocal<Gson>() {
        @Override
        protected Gson initialValue() {
            return new Gson();
        }
    };

    public BaseContextViewModel(@NonNull Application application) {
        super(application);
    }
}
