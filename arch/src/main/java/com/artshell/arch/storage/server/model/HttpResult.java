package com.artshell.arch.storage.server.model;

public class HttpResult<I> {
    private String code;
    private String msg;
    private I data;

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

    public I getData() {
        return data;
    }

    public void setData(I data) {
        this.data = data;
    }
}
