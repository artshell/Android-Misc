package com.artshell.arch.storage.server.model;

public class HttpPagingResult<T> {
    /**
     * code : 0
     * msg : 操作成功
     * pageData :
     */

    private int         code;
    private String      msg;
    private Pageable<T> pageData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Pageable<T> getPageData() {
        return pageData;
    }

    public void setPageData(Pageable<T> pageData) {
        this.pageData = pageData;
    }
}
