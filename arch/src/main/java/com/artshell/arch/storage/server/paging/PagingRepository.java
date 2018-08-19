package com.artshell.arch.storage.server.paging;

import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.artshell.arch.storage.server.HttpManager;
import com.artshell.arch.storage.server.model.HttpPagingResult;
import com.artshell.arch.utils.TaskExecutors;

import java.util.Map;

import io.reactivex.Flowable;

public class PagingRepository {

    public static <Value, Response extends HttpPagingResult<Value>> Listing<Value> postCouples(
            Class<Response> target,
            String urlOrPath,
            Map<String, String> pairs) {

        // 分页算法(与后端约定的配置信息)
        PageState mState = new PageState();

        PageableDataSourceFactory<Value, Response> sourceFactory =
                new PageableDataSourceFactory<Value, Response>(mState, TaskExecutors.networkIO()) {
                    @Override
                    public Flowable<Response> source(Integer nextPage, int pageSize) {
                        // 从服务器端获取数据的具体实现部分
                        // p -> 分页参数
                        // size -> 每一页多少条记录
                        pairs.put("p", String.valueOf(nextPage));
                        pairs.put("size", String.valueOf(pageSize));
                        return HttpManager.post(target, urlOrPath, pairs)
                                .subscribeOn(mNetworkScheduler)
                                .observeOn(mNetworkScheduler);
                    }
                };

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(mState.getPageSize())
                .setPrefetchDistance(mState.getPageSize() - (mState.getPageSize() / 3))
                .setInitialLoadSizeHint(mState.getPageSize())
                .build();

        LivePagedListBuilder<Integer, Value> listBuilder = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(TaskExecutors.networkIO());

        return new Listing<>(
                sourceFactory.getInitialState(),
                sourceFactory.getNextPageState(),
                listBuilder.build(),
                () -> {
                    PageableDataSourceFactory<Value, Response>.PageableDataSource dataSource = sourceFactory.getDataSource().getValue();
                    if (dataSource != null) {
                        dataSource.invalidate();
                    }
                },
                sourceFactory::retryFailed);
    }
}
