package com.artshell.arch.storage.server.paging;

import java.util.List;

/**
 * @param <Content> 单条记录参数类型
 */
public class Pageable<Content> {
    private String        total;    /* 列表总记录数 */
    private List<Content> list;     /* 当前页记录列表 */
    private String        p;        /* 当前是第几页 */
    private String        size;     /* 当前页多少条记录 */

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Content> getList() {
        return list;
    }

    public void setList(List<Content> list) {
        this.list = list;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
