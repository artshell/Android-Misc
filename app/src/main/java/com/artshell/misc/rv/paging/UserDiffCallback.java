package com.artshell.misc.rv.paging;

import android.support.v7.util.DiffUtil;

import com.artshell.misc.arch.entity.User;

/**
 * @author artshell on 2018/7/21
 */
public class UserDiffCallback extends DiffUtil.ItemCallback<User> {
    @Override
    public boolean areItemsTheSame(User oldItem, User newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(User oldItem, User newItem) {
        return oldItem.equals(newItem);
    }
}
