package com.artshell.misc.rv.official_paging_example.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artshell.misc.R;
import com.artshell.misc.rv.official_paging_example.repository.NetworkState;

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Runnable retryCallback;

    private ProgressBar progressBar;
    private Button retry;
    private TextView errorMsg;

    public NetworkStateItemViewHolder(View itemView, @NonNull Runnable retryCallback) {
        super(itemView);
        this.retryCallback = retryCallback;
        progressBar = itemView.findViewById(R.id.progress_bar);
        retry = itemView.findViewById(R.id.retry_button);
        errorMsg = itemView.findViewById(R.id.error_msg);
        retry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        retryCallback.run();
    }

    public void bindTo(@Nullable NetworkState networkState) {
        progressBar.setVisibility(toVisbility(networkState != null && networkState.mStatus == NetworkState.Status.RUNNING));
        retry.setVisibility(toVisbility(networkState != null && networkState.mStatus == NetworkState.Status.FAILED));
        errorMsg.setVisibility(toVisbility(networkState != null && networkState.msg == null));
        if (networkState != null) {
            errorMsg.setText(networkState.msg);
        }
    }

    private int toVisbility(boolean constraint) {
        return constraint ? View.VISIBLE : View.GONE;
    }

    public static NetworkStateItemViewHolder create(ViewGroup parent, Runnable retryCallback) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_state_item, parent, false);
        return new NetworkStateItemViewHolder(view, retryCallback);
    }
}
