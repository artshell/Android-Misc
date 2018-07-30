package com.artshell.arch.widget.rv;

import android.support.annotation.NonNull;
import android.support.v7.util.ListUpdateCallback;

/**
 * @author artshell on 2018/7/30
 */
public class DecorateListUpdateCallback implements ListUpdateCallback {

    @NonNull
    private final DecorateAdapter mAdapter;

    public DecorateListUpdateCallback(@NonNull DecorateAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onInserted(int position, int count) {
        mAdapter.notifyItemRangeInserted(mAdapter.getHeadersCount() + position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        mAdapter.notifyItemRangeRemoved(mAdapter.getHeadersCount() + position , count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        int headersCount = mAdapter.getHeadersCount();
        mAdapter.notifyItemMoved(headersCount + fromPosition, headersCount + toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        mAdapter.notifyItemRangeChanged(mAdapter.getHeadersCount() + position, count);
    }
}
