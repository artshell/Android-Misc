package com.artshell.arch.ui;

public interface NextConsumer<T> {

    /**
     * 处理数据结果
     */
    void onNext(T data);
}
