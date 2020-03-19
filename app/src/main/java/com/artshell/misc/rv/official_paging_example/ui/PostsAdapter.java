package com.artshell.misc.rv.official_paging_example.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.artshell.misc.R;
import com.artshell.misc.rv.official_paging_example.db.RedditPost;
import com.artshell.misc.rv.official_paging_example.repository.GlideRequests;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;

import java.util.List;

/**
 * A simple adapter implementation that shows Reddit posts.
 */
public class PostsAdapter extends PagedListAdapter<RedditPost, RecyclerView.ViewHolder> {

    private GlideRequests glide;
    private Runnable      retryCallback;
    @Nullable
    private NetworkState  networkState;

    public PostsAdapter(@NonNull DiffUtil.ItemCallback<RedditPost> diffCallback, GlideRequests glide, Runnable retryCallback) {
        super(diffCallback);
        this.glide = glide;
        this.retryCallback = retryCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.reddit_post_item:
                return RedditPostViewHolder.create(parent, glide);
            case R.layout.network_state_item:
                return NetworkStateItemViewHolder.create(parent, retryCallback);
            default:
                throw new IllegalArgumentException("unknown view type " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == R.layout.reddit_post_item) {
            ((RedditPostViewHolder) holder).bind(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindTo(networkState);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            RedditPost item = getItem(position);
            ((RedditPostViewHolder) holder).updateScore(item);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return hasExtraRow() && position == (getItemCount() - 1)
                ? R.layout.network_state_item
                : R.layout.reddit_post_item;
    }

    public static DiffCallback getCallback() {
        return new DiffCallback();
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(@Nullable NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean hadExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public static class DiffCallback extends DiffUtil.ItemCallback<RedditPost> {
        @Override
        public boolean areItemsTheSame(RedditPost oldItem, RedditPost newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(RedditPost oldItem, RedditPost newItem) {
            return oldItem.name == newItem.name || oldItem.name.equals(newItem.name);
        }

        @Override
        public Object getChangePayload(RedditPost oldItem, RedditPost newItem) {
            return sameExceptScore(oldItem, newItem) ? new Object() : null;
        }

        private boolean sameExceptScore(RedditPost oldItem, RedditPost newItem) {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            RedditPost oldItemCopy = new RedditPost(oldItem);
            oldItemCopy.score = newItem.score;
            return oldItemCopy.equals(newItem);
        }
    }
}
