package com.artshell.arch.cleanup;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.Status;
import com.artshell.arch.utils.MainLiveDataStreams;

/**
 * Note: require jdk 8
 *
 * @param <T>
 * @see MainLiveDataStreams
 *
 * @author artshell on 31/07/2018
 */
public interface ResourceObserver<T> extends
        Observer<Resource<T>>, NextConsumer<T>, ErrorConsumer, CompleteConsumer {

    @Override
    default void onChanged(@Nullable Resource<T> resource) {
        if (resource == null) return;
        @Status String state = resource.status;
        switch (state) {
            case Status.LOADING:
                onLoading();
                break;
            case Status.SUCCESS:
                T response = resource.data;
                onNext(response);
                break;
            case Status.ERROR:
                accept(resource.thr);
                break;
            case Status.COMPLETE:
                run();
                break;
        }
    }

    /**
     * 加载中(比如: 显示加载dialog)
     */
    default void onLoading() {

    }
}
