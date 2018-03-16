package com.artshell.arch.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.artshell.arch.storage.HttpManager;

import java.util.Map;

import io.reactivex.Notification;

/**
 * 1、常用请求(从服务器端获取数据)
 * 2、根据需要可选择重写部分方法(特别适用于多接口数据或一个接口又依赖其它接口的数据)
 * Created by artshell on 2018/3/16.
 */

public class ServerViewModel extends ViewModel {

    // 单接口数据(Get请求)
    public <T> LiveData<Notification<T>> getWithout(Class<T> target, String url) {
        return LiveDataReactiveStreams.fromPublisher(HttpManager.get(target, url).materialize());
    }

    // 单接口数据(Get请求带参数)
    public <T> LiveData<Notification<T>> getWithParameter(Class<T> target, String url, Map<String, String> queryPairs) {
        return LiveDataReactiveStreams.fromPublisher(HttpManager.get(target, url, queryPairs).materialize());
    }

    // 单接口数据(Post请求带字段)
    public <T> LiveData<Notification<T>> post(Class<T> target, String url, Map<String, String> postPairs) {
        return LiveDataReactiveStreams.fromPublisher(HttpManager.post(target, url, postPairs).materialize());
    }
}
