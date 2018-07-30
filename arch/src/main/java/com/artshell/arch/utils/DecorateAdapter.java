package com.artshell.arch.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artshell.arch.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author artshell on 15/12/2017
 */

public class DecorateAdapter extends RecyclerView.Adapter {
    private static final String TAG = "DecorateAdapter";

    private static final int HEADER_TYPE_FACTOR = -1 << 10;
    private static final int FOOTER_TYPE_FACTOR = -1 << 11;

    private final List<FixedViewInfo> mHeaders;
    private final List<FixedViewInfo> mFooters;

    private final RecyclerView.Adapter mContentAdapter;

    public DecorateAdapter(RecyclerView.Adapter contentAdapter) {
        this(contentAdapter, null, null);
    }

    public DecorateAdapter(RecyclerView.Adapter contentAdapter, List<FixedViewInfo> headers, List<FixedViewInfo> footers) {
        mContentAdapter = contentAdapter;
        mHeaders = headers == null ? new ArrayList<>(2) : headers;
        mFooters = footers == null ? new ArrayList<>(2) : footers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (isHeaderType(viewType)) {
            return new HeaderFooterViewHolder(inflater.inflate(mHeaders.get(Math.abs(viewType - HEADER_TYPE_FACTOR)).getLayoutResId(), parent, false));
        } else if (isFooterType(viewType)) {
            return new HeaderFooterViewHolder(inflater.inflate(mFooters.get(Math.abs(viewType - FOOTER_TYPE_FACTOR)).getLayoutResId(), parent, false));
        }
        return mContentAdapter.onCreateViewHolder(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < getHeadersCount() || position >= getHeadersCount() + mContentAdapter.getItemCount()) {
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onBindViewHolder: position => " + position + ", getHeadersCount = " + getHeadersCount());
        }
        mContentAdapter.onBindViewHolder(holder, position - getHeadersCount(), Collections.emptyList());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaders.get(position).getViewType();
        } else if (isFooterPosition(position)) {
            return mFooters.get(position - getHeadersCount() - mContentAdapter.getItemCount()).getViewType();
        }
        return mContentAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return isEmpty() ? getHeadersCount() + getFootersCount() : getHeadersCount() + mContentAdapter.getItemCount() + getFootersCount();
    }

    public int getHeadersCount() {
        return mHeaders.size();
    }

    public int getFootersCount() {
        return mFooters.size();
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaders.size();
    }

    private boolean isFooterPosition(int position) {
        return position >= mHeaders.size() + mContentAdapter.getItemCount();
    }

    public boolean isEmpty() {
        return mContentAdapter == null || mContentAdapter.getItemCount() == 0;
    }

    public boolean removeHeader(int layoutResId) {
        for (int i = 0; i < mHeaders.size(); i++) {
            FixedViewInfo info = mHeaders.get(i);
            if (info.getLayoutResId() == layoutResId) {
                mHeaders.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeFooter(int layoutResId) {
        for (int i = 0; i < mFooters.size(); i++) {
            FixedViewInfo info = mFooters.get(i);
            if (info.getLayoutResId() == layoutResId) {
                mFooters.remove(i);
                return true;
            }
        }
        return false;
    }

    public void removeAllHeader() {
        if (!mHeaders.isEmpty()) {
            mHeaders.clear();
        }
    }

    public void removeAllFooter() {
        if (!mFooters.isEmpty()) {
            mFooters.clear();
        }
    }

    public void addHeaderView(int layoutResId) {
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("layoutResId <= 0");
        }
        FixedViewInfo info = new FixedViewInfo();
        info.setLayoutResId(layoutResId);
        info.setViewType(HEADER_TYPE_FACTOR + mHeaders.size());
        mHeaders.add(info);
    }

    public void addFooterView(int layoutResId) {
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("layoutResId <= 0");
        }
        FixedViewInfo info = new FixedViewInfo();
        info.setLayoutResId(layoutResId);
        info.setViewType(FOOTER_TYPE_FACTOR + mFooters.size());
        mFooters.add(info);
    }

    public boolean containsFooter(int layoutResId) {
        for (int i = 0; i < mFooters.size(); i++) {
            FixedViewInfo info = mFooters.get(i);
            if (info.getLayoutResId() == layoutResId) {
                return true;
            }
        }
        return false;
    }

    public boolean containsHeader(int layoutResId) {
        for (int i = 0; i < mHeaders.size(); i++) {
            FixedViewInfo info = mHeaders.get(i);
            if (info.getLayoutResId() == layoutResId) {
                return true;
            }
        }
        return false;
    }

    public void tipByHeader(int layoutResId) {
        removeHeaderFooter();
        addHeaderView(layoutResId);
        notifyDataSetChanged();
    }

    public void tipByFooter(int layoutResId) {
        removeHeaderFooter();
        addFooterView(layoutResId);
        notifyDataSetChanged();
    }

    public void removeHeaderFooter() {
        removeAllHeader();
        removeAllFooter();
    }

    public void trimHeaderFooter() {
        if (getHeadersCount() > 0 || getFootersCount() > 0) {
            removeHeaderFooter();
            notifyDataSetChanged();
        }
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mContentAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mContentAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
        mContentAdapter.onAttachedToRecyclerView(rv);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView rv) {
        super.onDetachedFromRecyclerView(rv);
        mContentAdapter.onDetachedFromRecyclerView(rv);
    }

    public RecyclerView.Adapter getContentAdapter() {
        return mContentAdapter;
    }

    private boolean isHeaderType(int viewType) {
        return viewType >= HEADER_TYPE_FACTOR && viewType < (HEADER_TYPE_FACTOR + mHeaders.size());
    }

    private boolean isFooterType(int viewType) {
        return viewType >= FOOTER_TYPE_FACTOR && viewType < (FOOTER_TYPE_FACTOR + mFooters.size());
    }

    private static class HeaderFooterViewHolder extends ViewHolder {
        HeaderFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FixedViewInfo {
        private int viewType;
        private int layoutResId;
        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public int getLayoutResId() {
            return layoutResId;
        }

        public void setLayoutResId(int layoutResId) {
            this.layoutResId = layoutResId;
        }
    }
}
