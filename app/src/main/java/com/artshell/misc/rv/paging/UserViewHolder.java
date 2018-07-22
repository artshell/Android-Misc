package com.artshell.misc.rv.paging;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.artshell.misc.R;

/**
 * @author artshell on 2018/7/21
 */
public class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView lastName;

    public UserViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_name);
        lastName = itemView.findViewById(R.id.tv_last_name);
    }
}
