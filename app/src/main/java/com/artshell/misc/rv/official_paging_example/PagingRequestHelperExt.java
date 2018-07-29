package com.artshell.misc.rv.official_paging_example;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.artshell.misc.rv.official_paging_example.repository.NetworkState;

import java.util.concurrent.Executor;

public class PagingRequestHelperExt extends PagingRequestHelper {

    public PagingRequestHelperExt(Executor retryService) {
        super(retryService);
    }

    public String getErrorMessage(StatusReport report) {
        String message = "";
        RequestType[] values = RequestType.values();
        for (RequestType type : values) {
            Throwable thr = report.getErrorFor(type);
            if (thr != null) {
                message = thr.getMessage();
                break;
            }
        }
        return message;
    }

    public LiveData<NetworkState> createStatusLiveData() {
        MutableLiveData<NetworkState> data = new MutableLiveData<>();
        addListener(report -> {
            if (report.hasRunning()) {
                data.postValue(NetworkState.LOADING);
            } else if (report.hasError()) {
                data.postValue(NetworkState.error(getErrorMessage(report)));
            } else {
                data.postValue(NetworkState.LOADED);
            }
        });
        return data;
    }
}
