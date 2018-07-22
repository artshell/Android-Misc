package com.artshell.misc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    private static final String TAG = "MainActivity";

    private List<Object> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProperty();
    }

    private void setProperty() {
        RecyclerView container = findViewById(R.id.layout_container);
        MultiTypeAdapter adapter = new MultiTypeAdapter(mItems);
        adapter.register(String.class, new ActivityBinder<>(getPackageManager(), this));
        adapter.register(ResolveInfo.class, new ActivityBinder<>(getPackageManager(), this));
        container.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        container.setAdapter(adapter);
        findCorrectActivity();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void itemClick(View v, int pos) {
        Object o = mItems.get(pos);
        if (o instanceof String) return;
        ResolveInfo info = (ResolveInfo) o;
        ComponentName comp = new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
        Intent tent = new Intent();
        tent.setComponent(comp);
        startActivity(tent);
    }

    /**
     * 查找所有Activity
     * https://stackoverflow.com/questions/23671165/get-all-activities-by-using-package-name
     */
    private void findAllActivity() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] infos = pi.activities;
            // ...
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找指定Activity
     * https://stackoverflow.com/questions/23671165/get-all-activities-by-using-package-name
     */
    private void findCorrectActivity() {
        Intent correct = new Intent(Intent.ACTION_MAIN, null);
        correct.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = getPackageManager().queryIntentActivities(correct, PackageManager.GET_RESOLVED_FILTER);
        int descActivity = -1;
        int descService = -1;
        int descRecycler = -1;
        for (ResolveInfo info : resolves) {
            int descRes = info.activityInfo.descriptionRes;
            if (descRes == R.string.activity || descRes == R.string.service || descRes == R.string.rv) {
                if (descRes == R.string.activity && descActivity == -1) {
                    mItems.add(getString(R.string.activity));
                    descActivity = 0; /* found */
                }

                if (descRes == R.string.service && descService == -1) {
                    mItems.add(getString(R.string.service));
                    descService = 0; /* found */
                }

                if (descRes == R.string.rv && descRecycler == -1) {
                    mItems.add(getString(R.string.rv));
                    descRecycler = 0; /* found */
                }
                mItems.add(info);
            }
        }
    }

    private static class ActivityBinder<T> extends ItemViewBinder<T, ActivityBinder.NameViewHolder> {
        private PackageManager mManager;
        private ItemClickListener mListener;

        ActivityBinder(PackageManager manager, ItemClickListener listener) {
            mManager = manager;
            mListener = listener;
        }

        @NonNull
        @Override
        protected NameViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new NameViewHolder(inflater.inflate(R.layout.item_main_activity_name, parent, false), mListener);
        }

        @Override
        protected void onBindViewHolder(@NonNull NameViewHolder holder, @NonNull T item) {
            if (item instanceof String) {
                holder.name.setText((String) item);
            } else if (item instanceof ResolveInfo){
                ResolveInfo info = (ResolveInfo) item;
                CharSequence sequence = info.activityInfo.loadLabel(mManager);
                holder.name.setText(sequence);
            }
        }

        static class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView name;
            ItemClickListener mListener;
            NameViewHolder(View itemView, ItemClickListener listener) {
                super(itemView);
                name = itemView.findViewById(R.id.tv_name);
                mListener = listener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.itemClick(v, getAdapterPosition());
                }
            }
        }
    }
}
