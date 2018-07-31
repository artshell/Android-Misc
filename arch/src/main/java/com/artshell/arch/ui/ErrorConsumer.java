package com.artshell.arch.ui;

import io.reactivex.functions.Consumer;

/**
 * 异常统一处理类(在此类做一些, 比如：给用户友好提示)
 * Note: require jdk 8
 * @author artshell on 31/07/2018
 */
public interface ErrorConsumer extends Consumer<Throwable> {

    /**
     * 异常默认处理方案
     * @param throwable
     * @throws Exception
     */
    @Override
    default void accept(Throwable throwable) {
        if (onError(throwable)) return;
        // 未处理异常交由以下代码处理
        // ...
    }

    /**
     * 实现此方法做一些特殊处理(比如：关闭dialog, 针对某些异常做特殊处理)
     * @param throwable
     * @return true已经处理该异常; false未处理该异常
     */
    default boolean onError(Throwable throwable) {
        return false;
    }
}
