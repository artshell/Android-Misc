package com.artshell.misc.service;

import android.app.Application;
import android.app.Service;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

public class ReactiveLiveDataViewModel extends AndroidViewModel implements ServiceConnection {

    private ReactiveLiveDataService.SourceBinder binder;
    private boolean                              isUnbind;

    public ReactiveLiveDataViewModel(@NonNull Application application) {
        super(application);
        Intent tent = new Intent(getApplication(), ReactiveLiveDataService.class);
        getApplication().bindService(tent, this, Service.BIND_AUTO_CREATE);
    }

    public LiveData<List<String>> getLiveSource(Bundle args) {
        return binder.getService().getLiveSource(args);
    }

    public Observable<String[]> getPublisherSource(Bundle args) {
        return binder.getService().getPublisherSource(args);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (ReactiveLiveDataService.SourceBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        binder = null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!isUnbind) {
            isUnbind = true;
            getApplication().unbindService(this);
            binder = null;
        }
    }
}
