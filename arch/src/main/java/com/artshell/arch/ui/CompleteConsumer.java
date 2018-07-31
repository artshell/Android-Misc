package com.artshell.arch.ui;

public interface CompleteConsumer {

    /**
     * 任务完成(比如: 关闭dialog)
     */
    default void onComplete() {

    }
}
