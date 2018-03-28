package com.artshell.arch.view_model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.MainLiveDataStreams;
import com.artshell.arch.storage.Mixture;
import com.artshell.arch.storage.Mixture2;
import com.artshell.arch.storage.Result;
import com.artshell.arch.storage.cache.CacheManager;
import com.artshell.arch.utils.RxSchedulers;

import java.util.Map;

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
    public <T> LiveData<Result<T>> get(String cacheKey, Class<T> target, String url) {
        return MainLiveDataStreams.fromPublisher(
                CacheManager.store()
                        .get(new Mixture(cacheKey, url))
                        .map(raw -> LOCAL.get().fromJson(raw, target))
                        .toFlowable()
                        .compose(RxSchedulers.ioToMain())
        );
    }

    // Get请求带参数
    public <T> LiveData<Result<T>> getWithParameter(String cacheKey, Class<T> target, String url, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                CacheManager.storeWithParameter()
                        .get(new Mixture2(cacheKey, url, pairs))
                        .map(raw -> LOCAL.get().fromJson(raw, target))
                        .toFlowable()
                        .compose(RxSchedulers.ioToMain())
        );
    }

    // Post请求带字段
    public <T> LiveData<Result<T>> post(String cacheKey, Class<T> target, String url, Map<String, String> pairs) {
        return MainLiveDataStreams.fromPublisher(
                CacheManager.storeWithField()
                        .get(new Mixture2(cacheKey, url, pairs))
                        .map(raw -> LOCAL.get().fromJson(raw, target))
                        .toFlowable()
                        .compose(RxSchedulers.ioToMain())
        );
    }
}
