package com.artshell.misc.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artshell.misc.R;

import me.drakeet.multitype.ItemViewBinder;


public class NameViewBinder extends ItemViewBinder<String, NameViewBinder.NameHolder> {

    @NonNull
    @Override
    protected NameHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new NameViewBinder.NameHolder(inflater.inflate(R.layout.item_name_view, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull NameHolder holder, @NonNull String item) {
        holder.tv.setText(item);
    }

    static class NameHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public NameHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
        }
    }
}
