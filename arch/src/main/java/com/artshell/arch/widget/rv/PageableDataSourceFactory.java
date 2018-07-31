package com.artshell.arch.widget.rv;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.server.model.HttpPagingResult;

import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <Key>      页码(第1页,第2页等)
 * @param <Value>    {@link PagedList} 最终得到结果
 * @param <Response> 服务器端返回的json字符串
 */
public abstract class PageableDataSourceFactory<Key extends Integer, Value, Response extends HttpPagingResult>
        extends DataSource.Factory<Key, Value> {

    private   MutableLiveData<Resource<Value>> mNetworkState;       /* 网络状态(这里并不用来传递获取成功的结果) */
    private   PageState                        mState;              /* 分页算法 */
    protected Scheduler                        mNetworkScheduler;

    public PageableDataSourceFactory(Executor networkExecutor) {
        mNetworkState = new MutableLiveData<>();
        mState = new PageState();
        mNetworkScheduler = Schedulers.from(networkExecutor);
    }

    public MutableLiveData<Resource<Value>> getNetworkState() {
        return mNetworkState;
    }

    @Override
    public DataSource<Key, Value> create() {
        return null;
    }

    /**
     * 获取初始分页数据/加载下一页
     */
    private class PageableDataSource extends PageKeyedDataSource<Key, Response> {

        @Override
        public final void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Key> params, @NonNull LoadInitialCallback<Key, Response> callback) {

        }

        @Override
        public final void loadBefore(@NonNull LoadParams<Key> params, @NonNull LoadCallback<Key, Response> callback) {
            // 不在某一页之前插入数据，故此忽略实现
        }

        @Override
        public final void loadAfter(@NonNull LoadParams<Key> params, @NonNull LoadCallback<Key, Response> callback) {

        }
    }

    /**
     * @param currentPage 当前页
     * @param <Key>       页码(第1页,第2页等)
     * @param <Response>  服务器端返回的json字符串
     * @return
     */
    abstract <Key, Response> Flowable<Response> source(Key currentPage);
}
