package com.artshell.arch.cleanup;

import io.reactivex.functions.Action;

/**
 * Note: require jdk 8
 * @see Action
 */
public interface CompleteConsumer extends Action {

    /**
     * 任务完成(比如: 关闭dialog)
     */
    @Override
    default void run() {

    }
}
