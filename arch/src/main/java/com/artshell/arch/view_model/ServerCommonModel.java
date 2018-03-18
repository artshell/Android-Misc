package com.artshell.arch.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.artshell.arch.storage.HttpManager;
import com.artshell.arch.utils.RxSchedulers;

import java.util.Map;

import io.reactivex.Notification;

/**
 * 常用请求(单接口数据,从服务器端获取)
 * (不适用于多接口数据或一个接口又依赖其它接口的数据)根据需要可选择重写相应方法
 * Created by artshell on 2018/3/16.
 */

public class ServerCommonModel extends ViewModel {

    // Get请求
    public <T> LiveData<Notification<T>> fetch(Class<T> target, String url) {
        return LiveDataReactiveStreams.fromPublisher(
                HttpManager.get(target, url)
                        .materialize()
                        .compose(RxSchedulers.ioToMain()));
    }

    // Get请求带参数
    public <T> LiveData<Notification<T>> fetchByParameter(Class<T> target, String url, Map<String, String> pairs) {
        return LiveDataReactiveStreams.fromPublisher(
                HttpManager.get(target, url, pairs)
                        .materialize()
                        .compose(RxSchedulers.ioToMain()));
    }

    // Post请求带字段
    public <T> LiveData<Notification<T>> post(Class<T> target, String url, Map<String, String> pairs) {
        return LiveDataReactiveStreams.fromPublisher(
                HttpManager.post(target, url, pairs)
                        .materialize()
                        .compose(RxSchedulers.ioToMain()));
    }
}