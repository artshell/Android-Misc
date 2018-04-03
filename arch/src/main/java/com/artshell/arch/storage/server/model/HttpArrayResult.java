package com.artshell.arch.storage.server.model;

import java.util.List;

public class HttpArrayResult<I> {
    private String code;
    private String msg;
    private List<I> mData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<I> getData() {
        return mData;
    }

    public void setData(List<I> data) {
        this.mData = data;
    }
}
