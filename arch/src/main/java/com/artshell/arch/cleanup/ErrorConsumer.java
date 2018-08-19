package com.artshell.arch.cleanup;

import android.text.TextUtils;
import android.widget.Toast;

import com.artshell.arch.BuildConfig;
import com.artshell.arch.app.AppContext;
import com.artshell.arch.storage.Resource;

import io.reactivex.functions.Consumer;

/**
 * 异常统一处理类(在此类做一些, 比如：给用户友好提示)
 * Note: require jdk 8
 * @see Consumer
 * @author artshell on 31/07/2018
 */
public interface ErrorConsumer extends Consumer<Throwable> {

    /**
     * 异常默认处理方案
     * @param thr
     */
    @Override
    default void accept(Throwable thr) {
        // 异常特殊处理
        if (onError(thr)) return;

        // 未处理异常交由以下代码处理
        String msg = "";
        if (thr instanceof Exception) { /* 自定义的异常 */
            msg = thr.getMessage();
        }

        // 部分异常信息呈现给用户
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(AppContext.getAppContext(), msg, Toast.LENGTH_SHORT).show();
        }

        // debug状态下打印异常信息
        if (BuildConfig.DEBUG) {
            thr.printStackTrace();
        }
    }

    /**
     * 异常特殊处理(比如：关闭dialog)
     * @param thr
     * @return true已经处理该异常; false未处理该异常
     * @see ResourceObserver#onChanged(Resource)
     */
    default boolean onError(Throwable thr) {
        return false;
    }
}
