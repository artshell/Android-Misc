package com.artshell.arch.widget.rv;

import android.arch.paging.AsyncPagedListDiffer;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

/**
 * Modified form {@link android.arch.paging.PagedListAdapter}
 * @author artshell on 2018/7/30
 */
public abstract class AppendPagedListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final AsyncPagedListDiffer<T> mDiffer;

    /**
     *
     * @param adapter      {@link DecorateAdapter}
     * @param diffCallback The {@link DiffUtil.ItemCallback DiffUtil.ItemCallback} instance to
     *                     compare items in the list.
     */
    protected AppendPagedListAdapter(@NonNull DecorateAdapter adapter, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        this(adapter, new AsyncDifferConfig.Builder<>(diffCallback).build());
    }

    /**
     * @param adapter
     * @param config
     */
    @SuppressWarnings("unused, WeakerAccess")
    protected AppendPagedListAdapter(@NonNull DecorateAdapter adapter, @NonNull AsyncDifferConfig<T> config) {
        mDiffer = new AsyncPagedListDiffer<>(new DecorateListUpdateCallback(adapter), config);
    }

    /**
     * Set the new list to be displayed.
     * <p>
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param pagedList The new list to be displayed.
     */
    public void submitList(PagedList<T> pagedList) {
        mDiffer.submitList(pagedList);
    }

    @Nullable
    protected T getItem(int position) {
        return mDiffer.getItem(position);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getItemCount();
    }

    /**
     * Returns the PagedList currently being displayed by the Adapter.
     * <p>
     * This is not necessarily the most recent list passed to {@link #submitList(PagedList)},
     * because a diff is computed asynchronously between the new list and the current list before
     * updating the currentList value. May be null if no PagedList is being presented.
     *
     * @return The list currently being displayed.
     */
    @Nullable
    public PagedList<T> getCurrentList() {
        return mDiffer.getCurrentList();
    }
}
