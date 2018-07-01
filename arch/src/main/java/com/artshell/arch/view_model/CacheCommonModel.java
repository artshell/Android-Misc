package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.MainLiveDataStreams;
import com.artshell.arch.storage.cache.Mixture;
import com.artshell.arch.storage.cache.Mixture2;
import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.cache.PreferCacheManager;

import java.util.Map;

import io.reactivex.schedulers.Schedulers;

/**
 * 常用请求(单接口数据, 从缓存中获取, 没有则自动从服务器端获取)
 * (不适用于多接口数据或一个接口又依赖其它接口的数据)根据需要可选择重写相应方法
 * Created by artshell on 2018/3/16.
 */

public class CacheCommonModel extends BaseContextViewModel {

    public CacheCommonModel(@NonNull Application application) {
        super(application);
    }

    // Get请求
    public <T> LiveData<Resource<T>> get(Class<T> target, String path) {
        return MainLiveDataStreams.fromPublisher(
                PreferCacheManager.store()
                        .get(new Mixture(path))
                        .map(raw -> singleton().get().fromJson(raw, target))
                        .toFlowable()
                        .subscribeOn(Schedulers.io()));
    }

    // Get请求带参数
    public <T> LiveData<Resource<T>> getWithParameter(Class<T> target, String path, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                PreferCacheManager.storeWithParameter()
                        .get(new Mixture2(path, pairs))
                        .map(raw -> singleton().get().fromJson(raw, target))
                        .toFlowable()
                        .subscribeOn(Schedulers.io()));
    }

    // Post请求带字段
    public <T> LiveData<Resource<T>> post(Class<T> target, String path, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                PreferCacheManager.storeWithCouples()
                        .get(new Mixture2(path, pairs))
                        .map(raw -> singleton().get().fromJson(raw, target))
                        .toFlowable()
                        .subscribeOn(Schedulers.io()));
    }
}
