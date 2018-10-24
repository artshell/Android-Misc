package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.artshell.arch.cleanup.ResourceObserver;
import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.server.HttpManager;
import com.artshell.arch.utils.MainLiveDataStreams;

import java.util.Map;

import io.reactivex.schedulers.Schedulers;

/**
 * 常用请求(单接口数据,从服务器端获取)
 * @see ResourceObserver
 * Created by artshell on 2018/3/16.
 */
public class HttpResourceViewModel extends AndroidViewModel {

    public HttpResourceViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Get请求
     * @param target
     * @param path   url path
     * @param <T>
     * @return
     */
    public <T> LiveData<Resource<T>> fetch(Class<T> target, String path) {
        return MainLiveDataStreams.fromPublisher(
                HttpManager.get(target, path)
                        .onTerminateDetach()
                        .subscribeOn(Schedulers.io()));
    }

    /**
     * Get请求带参数
     * @param target
     * @param path   url path
     * @param pairs
     * @param <T>
     * @return
     */
    public <T> LiveData<Resource<T>> fetchByParameter(Class<T> target, String path, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                HttpManager.get(target, path, pairs)
                        .onTerminateDetach()
                        .subscribeOn(Schedulers.io()));
    }

    /**
     * Post请求
     * @param target
     * @param path   url path
     * @param pairs
     * @param <T>
     * @return
     */
    public <T> LiveData<Resource<T>> post(Class<T> target, String path, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                HttpManager.post(target, path, pairs)
                        .onTerminateDetach()
                        .subscribeOn(Schedulers.io()));
    }
}
