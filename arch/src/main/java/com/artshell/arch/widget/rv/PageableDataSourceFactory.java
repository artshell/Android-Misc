package com.artshell.arch.widget.rv;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.artshell.arch.common.EmptyDataException;
import com.artshell.arch.common.FetchFailedException;
import com.artshell.arch.common.NoMoreDataException;
import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.server.model.HttpPagingResult;
import com.artshell.arch.storage.server.model.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <Value>    {@link PagedList}的结果类型
 * @param <Response> 服务器端返回的json字符串
 */
public abstract class PageableDataSourceFactory<Value, Response extends HttpPagingResult<Value>> extends DataSource.Factory<Integer, Value> {

    /* 网络状态(这里并不用来传递获取成功的结果, 只传递正在加载/加载失败状态) */
    private MutableLiveData<Resource<Void>> mNetworkState;

    private Executor retryExecutor;
    protected Scheduler mNetworkScheduler;

    /* 持有一个重试引用 */
    private Runnable retry;

    public PageableDataSourceFactory(Executor workerExecutor) {
        mNetworkState = new MutableLiveData<>();
        retryExecutor = workerExecutor;
        mNetworkScheduler = Schedulers.from(workerExecutor);
    }

    public MutableLiveData<Resource<Void>> getNetworkState() {
        return mNetworkState;
    }

    /**
     * 重试
     */
    public void retryFailed() {
        Runnable prevRetry = retry;
        retry = null;
        if (prevRetry != null) {
            retryExecutor.execute(prevRetry);
        }
    }

    @Override
    public DataSource<Integer, Value> create() {
        return new PageableDataSource();
    }

    private class PageableDataSource extends PageKeyedDataSource<Integer, Value> {

        private PageState mState; /* 分页算法 */

        private PageableDataSource() {
            this(new PageState());
        }

        private PageableDataSource(PageState state) {
            mState = state;
        }

        /**
         * 获取第一页数据 & 计算分页信息
         * @param params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @Override
        public final void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Value> callback) {
            source(1)
                    .doOnSubscribe(subscription -> mNetworkState.postValue(Resource.loading()))
                    .subscribe(
                            response -> {
                                Pageable<Value> pageableData = response.getPageData();
                                String total = pageableData.getTotal();
                                List<Value> dataList = pageableData.getList() == null ? Collections.emptyList() : pageableData.getList();
                                if ("".equals(total) || "0".equals(total) || dataList.isEmpty()) {
                                    mNetworkState.postValue(Resource.error(new EmptyDataException("无数据")));
                                }
                                // 计算分页状态
                                mState.clear();
                                mState.calculate(Integer.valueOf(total));

                                // 回传结果, 设置上一页/下一页的页码
                                callback.onResult(dataList, 1, mState.hasNext() ? mState.getCurrPage() + 1 : null /* 没有下一页 */);
                            }, throwable -> {
                                retry = () -> loadInitial(params, callback);
                                mNetworkState.postValue(Resource.error(throwable));
                            });
        }

        @Override
        public final void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Value> callback) {
            // 不在某一页之前插入数据，故此忽略实现
        }

        /**
         * 获取下一页数据
         * @param params
         * @param callback
         */
        @SuppressLint("CheckResult")
        @Override
        public final void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Value> callback) {
            if (params.key == null) {
                mNetworkState.postValue(Resource.error(new NoMoreDataException("没有更多数据")));
                return;
            }
            source(params.key)
                    .doOnSubscribe(subscription -> mNetworkState.postValue(Resource.loading()))
                    .subscribe(
                            response -> {
                                Pageable<Value> pageableData = response.getPageData();
                                String total = pageableData.getTotal();
                                List<Value> dataList = pageableData.getList() == null ? Collections.emptyList() : pageableData.getList();
                                if ("".equals(total) || "0".equals(total) || dataList.isEmpty()) {
                                    retry = () -> loadAfter(params, callback);
                                    mNetworkState.postValue(Resource.error(new FetchFailedException("获取数据失败")));
                                } else {
                                    // 设置当前页
                                    mState.setCurrPage(mState.getCurrPage() + 1);

                                    // 回传结果, 设置下一页的页码
                                    callback.onResult(dataList, mState.hasNext() ? mState.getCurrPage() + 1 : null /* 没有下一页 */);
                                }
                            }, throwable -> {
                                retry = () -> loadAfter(params, callback);
                                mNetworkState.postValue(Resource.error(throwable));
                            });
        }
    }

    /**
     * 获取第一页/下一页数据
     * @param nextPage 下一页
     * @return
     */
    public abstract Flowable<Response> source(Integer nextPage);
}
