package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.server.HttpManager;
import com.artshell.arch.ui.NotificationObserver;

import java.util.Map;

import io.reactivex.Notification;
import io.reactivex.schedulers.Schedulers;

/**
 * 常用请求(单接口数据,从服务器端获取)
 * @see NotificationObserver
 * Created by artshell on 2018/3/16.
 */
public class HttpNotificationViewModel extends AndroidViewModel {

    public HttpNotificationViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Get请求
     * @param target
     * @param path   url path
     * @param <T>
     * @return
     */
    public <T> LiveData<Notification<T>> fetch(Class<T> target, String path) {
        return LiveDataReactiveStreams.fromPublisher(
                HttpManager.get(target, path)
                        .materialize()
                        .subscribeOn(Schedulers.io())
                        .onTerminateDetach());
    }

    /**
     * Get请求带参数
     * @param target
     * @param path   url path
     * @param pairs
     * @param <T>
     * @return
     */
    public <T> LiveData<Notification<T>> fetchByParameter(Class<T> target, String path, Map<String, String> pairs) {
        return LiveDataReactiveStreams.fromPublisher(
                HttpManager.get(target, path, pairs)
                        .materialize()
                        .subscribeOn(Schedulers.io())
                        .onTerminateDetach());
    }

    /**
     * Post请求
     * @param target
     * @param path   url path
     * @param pairs
     * @param <T>
     * @return
     */
    public <T> LiveData<Notification<T>> post(Class<T> target, String path, Map<String, String> pairs) {
        return LiveDataReactiveStreams.fromPublisher(
                HttpManager.post(target, path, pairs)
                        .materialize()
                        .subscribeOn(Schedulers.io())
                        .onTerminateDetach());
    }
}
