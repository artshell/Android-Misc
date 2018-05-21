package com.artshell.misc.service;

import android.os.Binder;

/**
 * @author artshell on 2018/5/21
 */
public class RxLiveDataService extends ReactiveService {

    public class SourceBinder extends Binder {

        RxLiveDataService getService() {
            return RxLiveDataService.this;
        }


    }
}
