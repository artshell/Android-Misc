package com.artshell.arch.widget.rv;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.artshell.arch.common.EmptyDataException;
import com.artshell.arch.common.FetchFailedException;
import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.server.model.HttpPagingResult;
import com.artshell.arch.storage.server.model.Pageable;
import com.artshell.arch.widget.rv.PageState.PullAction;

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
public abstract class PageableDataSourceFactory<Value, Response extends HttpPagingResult<Value>>
        extends DataSource.Factory<Integer, Value> {

    // 分页算法(与后端约定的配置信息)
    private PageState mState;

    // 加载第一页的网络状态(这里并不用来传递获取成功的结果, 只传递正在加载/加载失败状态)
    private MutableLiveData<Resource<Void>> mInitialState;

    // 加载下一页的网络状态(这里并不用来传递获取成功的结果, 只传递正在加载/加载失败状态)
    private MutableLiveData<Resource<Void>> mNextPageState;

    private MutableLiveData<PageableDataSource> mDataSource;

    private Executor retryExecutor;
    protected Scheduler mNetworkScheduler;

    // 持有一个重试引用
    private Runnable retry;

    public PageableDataSourceFactory(@NonNull PageState state, @NonNull Executor workerExecutor) {
        mState = state;
        mInitialState = new MutableLiveData<>();
        mDataSource = new MutableLiveData<>();
        retryExecutor = workerExecutor;
        mNetworkScheduler = Schedulers.from(workerExecutor);
    }

    public MutableLiveData<Resource<Void>> getInitialState() {
        return mInitialState;
    }

    public MutableLiveData<Resource<Void>> getNextPageState() {
        return mNextPageState;
    }

    public MutableLiveData<PageableDataSource> getDataSource() {
        return mDataSource;
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
        PageableDataSource source = new PageableDataSource(mState);
        mDataSource.postValue(source);
        return source;
    }

    public class PageableDataSource extends PageKeyedDataSource<Integer, Value> {
        private PageState mState; /* 分页算法(与后端约定的配置信息) */

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
        public final void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Value> callback) {
            source(1, params.requestedLoadSize)
                    .doOnSubscribe(subscription -> mInitialState.postValue(Resource.loading()))
                    .subscribe(
                            response -> {
                                List<Value> values = parseResponse(response, PullAction.PULL_DOWN);
                                if (values.size() > 0) {
                                    // 回传结果, 设置上一页/下一页的页码
                                    callback.onResult(values, 1, mState.hasNext() ? mState.getCurrPage() + 1 : null /* 没有下一页 */);
                                }
                            }, throwable -> {
                                retry = () -> loadInitial(params, callback);
                                mInitialState.postValue(Resource.error(throwable));
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
            source(params.key, params.requestedLoadSize)
                    .doOnSubscribe(subscription -> mNextPageState.postValue(Resource.loading()))
                    .subscribe(
                            response -> {
                                List<Value> values = parseResponse(response, PullAction.PULL_DOWN);
                                if (values.isEmpty()) {
                                    retry = () -> loadAfter(params, callback);
                                    return;
                                }
                                // 回传结果, 设置下一页的页码
                                callback.onResult(values, mState.hasNext() ? mState.getCurrPage() + 1 : null /* 没有下一页 */);
                            }, throwable -> {
                                retry = () -> loadAfter(params, callback);
                                mNextPageState.postValue(Resource.error(throwable));
                            });
        }


        /**
         * 解析Response + 配置分页
         */
        private List<Value> parseResponse(Response res, @PullAction String action) {
            Pageable<Value> pageableData = res.getPageData();
            String total = pageableData.getTotal();
            List<Value> dataList = pageableData.getList() == null ? Collections.emptyList() : pageableData.getList();
            if ("".equals(total) || "0".equals(total) || dataList.isEmpty()) {
                if (PullAction.PULL_DOWN.equals(action)) {
                    mInitialState.postValue(Resource.error(new EmptyDataException("暂无数据")));
                } else {
                    mNextPageState.postValue(Resource.error(new FetchFailedException("获取下一页数据失败")));
                }
            } else {
                if (PullAction.PULL_DOWN.equals(action)) {
                    // 计算分页信息
                    mState.clear();
                    mState.calculate(Integer.valueOf(total));
                } else {
                    // 更改分页状态中的当前页
                    mState.setCurrPage(mState.getCurrPage() + 1);
                }
            }
            return dataList;
        }
    }

    /**
     * 获取第一页/下一页数据
     * @param nextPage 下一页
     * @return
     */
    public abstract Flowable<Response> source(Integer nextPage, int pageSize);
}
