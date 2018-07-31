package com.artshell.arch.widget.rv;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by artshell on 2017/2/16.
 */

public class PageState {

    @StringDef({PullAction.PULL_DOWN, PullAction.PULL_UP})
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PullAction {
        /**
         * 下拉刷新
         */
        String PULL_DOWN = "pull_down";

        /**
         * 上拉加载
         */
        String PULL_UP   = "pull_up";
    }

    @PullAction
    private String mCurrState = PullAction.PULL_DOWN;   /* 当前状态(默认为下拉刷新) */

    private int mTotalRows;                             /* 总记录数 */
    private int mPageSize = 12;                         /* 服务器端默认每页12条记录, 可配置 */
    private int mTotalPages;                            /* 一共可以显示多少页 */
    private int mCurrPage;                              /* 当前页码 */

    /**
     * 获取当前状态
     * @see PullAction#PULL_DOWN
     * @see PullAction#PULL_UP
     * @return
     */
    public String getCurrState() {
        return mCurrState;
    }

    /**
     * 设置状态
     * @see PullAction#PULL_DOWN
     * @see PullAction#PULL_UP
     * @param currState
     */
    public void setCurrState(@PullAction String currState) {
        mCurrState = currState;
    }

    /**
     * @return true, 有下一页数据; false没有更多数据
     * @see #setCurrState(String)
     */
    public boolean hasNext() {
        return mCurrPage != 0 && mTotalPages != 0 && mCurrPage < mTotalPages;
    }

    /**
     * @return true, 没有进行过初始加载/或初始加载失败(当前页与总页数等于0)
     */
    public boolean isNeedInitLoad() {
        return mCurrPage == 0 && mTotalPages == 0;
    }

    public int getTotalRows() {
        return mTotalRows;
    }

    public void setTotalRows(int totalRows) {
        mTotalRows = totalRows;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public int getCurrPage() {
        return mCurrPage;
    }

    public void setCurrPage(int currPage) {
        mCurrPage = currPage;
    }

    public void clear() {
        mPageSize = 12;
        mCurrPage = 0;
        mTotalRows = 0;
        mTotalPages = 0;
        mCurrState = PullAction.PULL_DOWN;
    }

    @Override
    public String toString() {
        return "TotalPages => " + mTotalPages + ", CurrPage => " + mCurrPage;
    }

    /**
     * 计算可以显示多少页
     * @param totalRows
     */
    public void calculate(final int totalRows) {
        if (totalRows <= 0) return;
        setTotalRows(totalRows);

        int div = totalRows / mPageSize;
        int mod = totalRows % mPageSize;
        int totalPage = mod > 0 ? div + 1 : div;
        setTotalPages(totalPage);
        setCurrPage(getCurrPage() + 1);
    }
}
