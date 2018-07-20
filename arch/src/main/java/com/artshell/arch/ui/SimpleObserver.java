package com.artshell.arch.ui;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.artshell.arch.storage.Resource;
import com.artshell.arch.storage.Status;

public abstract class SimpleObserver<T> implements Observer<Resource<T>> {

    @Override
    public void onChanged(@Nullable Resource<T> resource) {
        if (resource == null) return;
        @Status String state = resource.status;
        switch (state) {
            case Status.LOADING: /* 显示加载dialog */
                break;
            case Status.SUCCESS: /* 处理结果数据 */
                T response = resource.data;
                if (response == null) return;
                onData(response);
                break;
            case Status.ERROR: /* 关闭dialog/显示错误信息 */
                break;
            case Status.COMPLETE: /* 关闭dialog */
                break;
        }
    }

    public abstract void onData(T data);
}
